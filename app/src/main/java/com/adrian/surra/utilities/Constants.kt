package com.adrian.surra.utilities

// Base URL'S
const val BASE_URL = "https://node-chat-api-exam.herokuapp.com/"
const val BASE_URL_LOCAL = "http://10.0.2.2:3005/"

// Socket URL'S
const val SOCKET_URL = "https://node-chat-api-exam.herokuapp.com/"
const val SOCKET_URL_LOCAL = "http://10.234.147.183:3005/"

// Endpoints production
const val REGISTER_URL = "${BASE_URL}account/register"
const val LOGIN_URL = "${BASE_URL}account/login"
const val CREATE_USER_URL = "${BASE_URL}user/add"
const val GET_USER_URL = "${BASE_URL}user/byEmail/"
const val GET_CHANNELS_URL = "${BASE_URL}channel/"
const val GET_MESSAGES_URL = "${BASE_URL}message/byChannel/"

// Broadcast constants
const val BROADCAST_USER_DATA_CHANGE = "BROADCAST_USER_DATA_CHANGE"
