package com.example.projectfigma.Repositories

import com.example.projectfigma.DAO.AddressDao
import com.example.projectfigma.Entites.Address
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddressRepository(private val addressDao: AddressDao) {

    suspend fun getAddresses(userId: Int): List<Address> {
        return addressDao.getByUserId(userId)
    }

    suspend fun addAddress(userId: Int, name: String, address: String, isDefault: Boolean): Long {
        return withContext(Dispatchers.IO) {
            if (isDefault) {
                addressDao.clearDefaults(userId)
            }
            addressDao.insert(Address(
                userId = userId,
                name = name,
                address = address,
                isDefault = isDefault
            ))
        }
    }

    suspend fun setDefaultAddress(addressId: Int, userId: Int) {
        withContext(Dispatchers.IO) {
            addressDao.clearDefaults(userId)
            addressDao.setAsDefault(addressId)
        }
    }

    suspend fun getDefaultAddress(userId: Int): Address? {
        return withContext(Dispatchers.IO) {
            addressDao.getDefaultAddress(userId)
        }
    }
}