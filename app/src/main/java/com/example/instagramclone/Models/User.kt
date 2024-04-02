package com.example.instagramclone.Models

class User {
    var image: String?=null
    var name: String?=null
    var email: String?=null
    var password: String?=null

    constructor()

    constructor(image: String?, name: String?, emails: String?, password: String?) {
        this.image = image
        this.name = name
        this.email = emails
        this.password = password
    }

    constructor(name: String?, emails: String?, password: String?) {
        this.name = name
        this.email = emails
        this.password = password
    }

    constructor(emails: String?, password: String?) {
        this.email = emails
        this.password = password
    }


}