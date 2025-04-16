package com.example.guest.data

interface GuestRepository {
    fun insertGuest(guest: Guest): Long
    fun updateGuest(guest: Guest): Int
    fun deleteGuest(id: Int): Int
    fun getAllGuests(): List<Guest>
}