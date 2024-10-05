package com.employeeexplorer.client.data

class Constants {
    interface URL_CONSTANTS{
        companion object{
            const val API_URL = "https://s3.amazonaws.com/sq-mobile-interview/"
        }
    }

    interface TYPE_CONSTANTS {
        companion object{
            const val FULL_TIME = "FULL_TIME"
            const val PART_TIME = "PART_TIME"
            const val CONTRACTOR = "CONTRACTOR"
        }
    }

    interface EXTRA{
        companion object{
            const val EXTRA_EMPLOYEE = "EXTRA_EMPLOYEE"
        }
    }


}