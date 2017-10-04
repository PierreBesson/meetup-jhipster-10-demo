import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TravelReservationComponent } from './travel-reservation.component';
import { TravelReservationDetailComponent } from './travel-reservation-detail.component';
import { TravelReservationPopupComponent } from './travel-reservation-dialog.component';
import { TravelReservationDeletePopupComponent } from './travel-reservation-delete-dialog.component';

export const travelReservationRoute: Routes = [
    {
        path: 'travel-reservation',
        component: TravelReservationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.travelReservation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'travel-reservation/:id',
        component: TravelReservationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.travelReservation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const travelReservationPopupRoute: Routes = [
    {
        path: 'travel-reservation-new',
        component: TravelReservationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.travelReservation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'travel-reservation/:id/edit',
        component: TravelReservationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.travelReservation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'travel-reservation/:id/delete',
        component: TravelReservationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.travelReservation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
