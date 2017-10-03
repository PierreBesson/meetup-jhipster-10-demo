import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TourComponent } from './tour.component';
import { TourDetailComponent } from './tour-detail.component';
import { TourPopupComponent } from './tour-dialog.component';
import { TourDeletePopupComponent } from './tour-delete-dialog.component';

export const tourRoute: Routes = [
    {
        path: 'tour',
        component: TourComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.tour.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tour/:id',
        component: TourDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.tour.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tourPopupRoute: Routes = [
    {
        path: 'tour-new',
        component: TourPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.tour.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tour/:id/edit',
        component: TourPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.tour.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tour/:id/delete',
        component: TourDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gatewayApp.tour.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
