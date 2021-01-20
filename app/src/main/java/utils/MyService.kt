package utils

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.traveldeal.data.repositories.TravelRepository

class MyService : Service() {
    private var travelAdded = false
    override fun onCreate() {
        super.onCreate()
        TravelRepository.getTravelRepository(App.instance).getLiveData().observeForever() {
            updateTravel(true)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            while (true) {
                if (travelAdded) {
                    updateTravel(false)
                    sendBroadcast(Intent().setAction("com.example.traveldeal.NewTravel"))
                }
//                for (travel in travelsList.value!!)
//                    if (travel != null) {
//                        if (travel.company.filter { it.value }.keys.first() == email)
//                            if (uidMapTravels[travel] != email) {
//                                //brodCast test
//                                uidMapTravels[travel] = email!!
//                                Utils.sendBroadcastCustomIntent("הנסיעה אושרה")
//                            }
//                    }
                Thread.sleep(5000)
            }
        }.start()
        // If we get killed, after returning from here, restart
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        // We don't provide binding, so return null
        return null
    }

    private fun updateTravel(state: Boolean) {
        travelAdded = state
    }
}