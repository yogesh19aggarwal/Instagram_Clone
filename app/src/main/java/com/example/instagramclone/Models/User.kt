package com.example.instagramclone.Models

class User {
    var image: String?=null
    var name: String?=null
    var emails: String?=null
    var password: String?=null

    constructor()

    constructor(image: String?, name: String?, emails: String?, password: String?) {
        this.image = image
        this.name = name
        this.emails = emails
        this.password = password
    }

    constructor(name: String?, emails: String?, password: String?) {
        this.name = name
        this.emails = emails
        this.password = password
    }


}