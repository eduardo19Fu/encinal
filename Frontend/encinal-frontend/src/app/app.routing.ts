import { ModuleWithProviders } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './components/home/home.component';
import { ClientsComponent } from './components/clients/clients.component';
import { CreateClientComponent } from './components/clients/create-client/create-client.component';
import { SellersComponent } from './components/sellers/sellers.component';
import { CreateSellerComponent } from './components/sellers/create-seller/create-seller.component';
import { BlocksComponent } from './components/blocks/blocks.component';
import { CreateBlockComponent } from './components/blocks/create-block/create-block.component';
import { IdentificationTypesComponent } from './components/identification-types/identification-types.component';
import { UpdateBlockComponent } from './components/blocks/update-block/update-block.component';
import { TerrainsComponent } from './components/terrains/terrains.component';
import { CreateTerrainComponent } from './components/terrains/create-terrain/create-terrain.component';
import { SalesComponent } from './components/sales/sales.component';
import { CreateSaleComponent } from './components/sales/create-sale/create-sale.component';
import { Step1Component } from './components/sales/create-sale/steps/step1/step1.component';
import { Step2Component } from './components/sales/create-sale/steps/step2/step2.component';
import { Step3Component } from './components/sales/create-sale/steps/step3/step3.component';

const appRoutes: Routes = [
    /*********** PATH FOR HOME PAGES  *************/
    {path: '', component: HomeComponent},
    {path: 'home', component: HomeComponent},

    /*********** PATH FOR TERRAINS PAGES  *************/
    {path: 'terrains/index', component: TerrainsComponent},
    {path: 'terrains/create', component: CreateTerrainComponent},
    {path: 'terrains/create/:id', component: CreateTerrainComponent},

    /*********** PATH FOR SALES PAGES  *************/
    {path: 'sales/index', component: SalesComponent},
    {path: 'sales/create', component: CreateSaleComponent},
    {path: 'sales/create/step-1', component: Step1Component},
    {path: 'sales/create/step-2', component: Step2Component},
    {path: 'sales/create/step-3', component: Step3Component},

    /*********** PATH FOR ADMIN PAGES  *************/
    // Clients
    {path: 'admin/clients/index', component: ClientsComponent},
    {path: 'admin/clients/create', component: CreateClientComponent},
    {path: 'admin/clients/create/:id', component: CreateClientComponent},

    // Sellers
    {path: 'admin/sellers/index', component: SellersComponent},
    {path: 'admin/sellers/create', component: CreateSellerComponent},
    {path: 'admin/sellers/create/:id', component: CreateSellerComponent},

    // Blocks
    {path: 'admin/blocks/index', component: BlocksComponent},
    {path: 'admin/blocks/create', component: CreateBlockComponent},
    {path: 'admin/blocks/update/:id', component: UpdateBlockComponent},

    // Identification Types
    {path: 'admin/identification-types/index', component: IdentificationTypesComponent},
];

export const appRoutingProviders: any[] = [];
export const routing: ModuleWithProviders<any> = RouterModule.forRoot(appRoutes);
