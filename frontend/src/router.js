
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import RoomRoomManager from "./components/listers/RoomRoomCards"
import RoomRoomDetail from "./components/listers/RoomRoomDetail"

import ReservationReservationManager from "./components/listers/ReservationReservationCards"
import ReservationReservationDetail from "./components/listers/ReservationReservationDetail"

import ReviewReviewManager from "./components/listers/ReviewReviewCards"
import ReviewReviewDetail from "./components/listers/ReviewReviewDetail"

import PaymentPaymentManager from "./components/listers/PaymentPaymentCards"
import PaymentPaymentDetail from "./components/listers/PaymentPaymentDetail"



export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/rooms/rooms',
                name: 'RoomRoomManager',
                component: RoomRoomManager
            },
            {
                path: '/rooms/rooms/:id',
                name: 'RoomRoomDetail',
                component: RoomRoomDetail
            },

            {
                path: '/reservations/reservations',
                name: 'ReservationReservationManager',
                component: ReservationReservationManager
            },
            {
                path: '/reservations/reservations/:id',
                name: 'ReservationReservationDetail',
                component: ReservationReservationDetail
            },

            {
                path: '/reviews/reviews',
                name: 'ReviewReviewManager',
                component: ReviewReviewManager
            },
            {
                path: '/reviews/reviews/:id',
                name: 'ReviewReviewDetail',
                component: ReviewReviewDetail
            },

            {
                path: '/payments/payments',
                name: 'PaymentPaymentManager',
                component: PaymentPaymentManager
            },
            {
                path: '/payments/payments/:id',
                name: 'PaymentPaymentDetail',
                component: PaymentPaymentDetail
            },




    ]
})
