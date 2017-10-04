import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    TravelReservationService,
    TravelReservationPopupService,
    TravelReservationComponent,
    TravelReservationDetailComponent,
    TravelReservationDialogComponent,
    TravelReservationPopupComponent,
    TravelReservationDeletePopupComponent,
    TravelReservationDeleteDialogComponent,
    travelReservationRoute,
    travelReservationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...travelReservationRoute,
    ...travelReservationPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TravelReservationComponent,
        TravelReservationDetailComponent,
        TravelReservationDialogComponent,
        TravelReservationDeleteDialogComponent,
        TravelReservationPopupComponent,
        TravelReservationDeletePopupComponent,
    ],
    entryComponents: [
        TravelReservationComponent,
        TravelReservationDialogComponent,
        TravelReservationPopupComponent,
        TravelReservationDeleteDialogComponent,
        TravelReservationDeletePopupComponent,
    ],
    providers: [
        TravelReservationService,
        TravelReservationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayTravelReservationModule {}
