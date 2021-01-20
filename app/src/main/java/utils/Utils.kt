package utils

class Utils {
    companion object {

        fun decodeKey(key: String?): String {
            return key!!.replace("\\u002e", ".").replace("\\u0024", "\$").replace("\\\\", "\\")
        }

        fun encodeKey(key: String?): String {
            return key!!.replace("\\", "\\\\").replace("\$", "\\u0024").replace(".", "\\u002e")
        }
    }
}