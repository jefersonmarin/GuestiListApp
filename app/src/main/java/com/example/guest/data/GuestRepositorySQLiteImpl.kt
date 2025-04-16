package com.example.guest.data

import GuestDatabaseHelper

class GuestRepositorySQLiteImpl(
    private val dbHelper : GuestDatabaseHelper
) : GuestRepository {

    override fun insertGuest(guest: Guest): Long {
        return dbHelper.insertGuest(guest)
    }

    override fun updateGuest(guest: Guest): Int {
        return dbHelper.updateGuest(guest)
    }

    override fun deleteGuest(id: Int): Int {
        return dbHelper.deleteGuest(id)
    }

    override fun getAllGuests(): List<Guest> {
        return dbHelper.getAllGuests()
    }
}