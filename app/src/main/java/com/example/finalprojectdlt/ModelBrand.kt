package com.example.finalprojectdlt

class ModelBrand {

    var id: String=""
    var brand:String=""
    var timestamp:Long = 0
    var uid:String=""
    constructor()

    constructor(id: String, brand: String, timestamp: Long, uid: String) {
        this.id = id
        this.brand = brand
        this.timestamp = timestamp
        this.uid = uid
    }

}