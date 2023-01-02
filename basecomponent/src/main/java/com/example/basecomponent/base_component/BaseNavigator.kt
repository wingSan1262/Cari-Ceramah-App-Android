package com.example.basecomponent.base_component

open class BaseNavigator {
    fun getNavigatorUriWithParam(
        rootNavigation : String,
        listArgKeyParam : List<String>
    ): String {
        var path = rootNavigation
        for(arguments in listArgKeyParam){
            path += "/{$arguments}"
        }
        return path
    }
}