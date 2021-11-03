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
import { UsuariosComponent } from './components/usuarios/usuarios.component';
import { CreateUserComponent } from './components/usuarios/create-user/create-user.component';
import { PaymentsComponent } from './components/payments/payments.component';
import { CreatePaymentComponent } from './components/payments/create-payment/create-payment.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import { ItemsComponent } from './components/items/items.component';
import { CreateItemComponent } from './components/items/create-item/create-item.component';
import { RoleGuard } from './guards/role.guard';
import { UpdateItemComponent } from './components/items/update-item/update-item.component';

const appRoutes: Routes = [
    /*********** PATH FOR HOME PAGES  *************/
    {path: '', component: LoginComponent},
    {path: 'login', component: LoginComponent},
    {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},

    /*********** PATH FOR TERRAINS PAGES  *************/
    {path: 'terrains/index', component: TerrainsComponent, canActivate: [AuthGuard]},
    {path: 'terrains/create', component: CreateTerrainComponent, canActivate: [AuthGuard]},
    {path: 'terrains/create/:id', component: CreateTerrainComponent, canActivate: [AuthGuard]},

    /*********** PATH FOR SALES PAGES  *************/
    // Sales
    {path: 'sales/index', component: SalesComponent, canActivate: [AuthGuard]},
    {path: 'sales/create', component: CreateSaleComponent, canActivate: [AuthGuard]},
    {path: 'sales/create/:terrainId', component: CreateSaleComponent, canActivate: [AuthGuard]},
    {path: 'sales/create/step-1', component: Step1Component, canActivate: [AuthGuard]},
    {path: 'sales/create/step-2', component: Step2Component, canActivate: [AuthGuard]},
    {path: 'sales/create/step-3', component: Step3Component, canActivate: [AuthGuard]},

    // Payments
    {path: 'sales/payments/index', component: PaymentsComponent, canActivate: [AuthGuard]},
    {path: 'sales/payments/create', component: CreatePaymentComponent, canActivate: [AuthGuard]},

    /*********** PATH FOR ADMIN PAGES  *************/
    // Users
    {path: 'admin/users/index', component: UsuariosComponent, canActivate: [AuthGuard]},
    {path: 'admin/users/create', component: CreateUserComponent, canActivate: [AuthGuard]},
    {path: 'admin/users/create/:id', component: CreateUserComponent, canActivate: [AuthGuard]},

    // Clients
    {path: 'admin/clients/index', component: ClientsComponent, canActivate: [AuthGuard]},
    {path: 'admin/clients/create', component: CreateClientComponent, canActivate: [AuthGuard]},
    {path: 'admin/clients/create/:id', component: CreateClientComponent, canActivate: [AuthGuard]},

    // Sellers
    {path: 'admin/sellers/index', component: SellersComponent, canActivate: [AuthGuard]},
    {path: 'admin/sellers/create', component: CreateSellerComponent, canActivate: [AuthGuard]},
    {path: 'admin/sellers/create/:id', component: CreateSellerComponent, canActivate: [AuthGuard]},

    // Blocks
    {path: 'admin/blocks/index', component: BlocksComponent, canActivate: [AuthGuard]},
    {path: 'admin/blocks/create', component: CreateBlockComponent, canActivate: [AuthGuard]},
    {path: 'admin/blocks/update/:id', component: UpdateBlockComponent, canActivate: [AuthGuard]},

    // Identification Types
    {path: 'admin/identification-types/index', component: IdentificationTypesComponent, canActivate: [AuthGuard]},

    // Items
    {path: 'admin/items/index', component: ItemsComponent, canActivate: [AuthGuard]},
    {path: 'admin/items/create', component: CreateItemComponent, canActivate: [AuthGuard, RoleGuard], data: {role: ['ROLE_SUPERADMIN']}},
    {path: 'admin/items/create/:id', component: CreateItemComponent, canActivate: [AuthGuard]},
    {path: 'admin/items/update/:id', component: UpdateItemComponent, canActivate: [AuthGuard]},

    {path: '**', component: HomeComponent}
];

export const appRoutingProviders: any[] = [];
export const routing: ModuleWithProviders<any> = RouterModule.forRoot(appRoutes);
