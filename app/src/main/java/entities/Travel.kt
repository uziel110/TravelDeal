package entities

@Travel.Entity(tableName = travel_table)
    class Travel(@PrimaryKey @ColumnInfo(name = "Travel") val word: String){

}