import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { appRoutingProviders, routing } from './app.routing';

import localeEs from '@angular/common/locales/es';
import { registerLocaleData } from '@angular/common';
registerLocaleData(localeEs, 'es');

import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderComponent } from './components/header/header.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { ClientsComponent } from './components/clients/clients.component';
import { CreateClientComponent } from './components/clients/create-client/create-client.component';
import { SellersComponent } from './components/sellers/sellers.component';
import { CreateSellerComponent } from './components/sellers/create-seller/create-seller.component';
import { BlocksComponent } from './components/blocks/blocks.component';
import { CreateBlockComponent } from './components/blocks/create-block/create-block.component';
import { DetailClientComponent } from './components/clients/detail-client/detail-client.component';
import { IdentificationTypesComponent } from './components/identification-types/identification-types.component';
import { DetailSellerComponent } from './components/sellers/detail-seller/detail-seller.component';
import { CreateIdentificationTypeComponent } from './components/identification-types/create-identification-type/create-identification-type.component';
import { UpdateBlockComponent } from './components/blocks/update-block/update-block.component';
import { TerrainsComponent } from './components/terrains/terrains.component';
import { CreateTerrainComponent } from './components/terrains/create-terrain/create-terrain.component';
import { SalesComponent } from './components/sales/sales.component';
import { CreateSaleComponent } from './components/sales/create-sale/create-sale.component';
import { Step1Component } from './components/sales/create-sale/steps/step1/step1.component';
import { Step2Component } from './components/sales/create-sale/steps/step2/step2.component';
import { Step3Component } from './components/sales/create-sale/steps/step3/step3.component';
import { CustomerSearchingComponent } from './components/sales/modals/customer-searching/customer-searching.component';
import { SellerSearchingComponent } from './components/sales/modals/seller-searching/seller-searching.component';
import { UsuariosComponent } from './components/usuarios/usuarios.component';
import { LoginComponent } from './components/login/login.component';
import { CreateUserComponent } from './components/usuarios/create-user/create-user.component';
import { PaymentsComponent } from './components/payments/payments.component';
import { CreatePaymentComponent } from './components/payments/create-payment/create-payment.component';

import { TokenInterceptor } from './interceptors/token.interceptor';
import { ItemsComponent } from './components/items/items.component';
import { CreateItemComponent } from './components/items/create-item/create-item.component';
import { UpdateItemComponent } from './components/items/update-item/update-item.component';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { CreateCustomerComponent } from './components/sales/modals/create-customer/create-customer.component';
import { PrincipalComponent } from './components/sales/modals/principal/principal.component';
import { DetailUserComponent } from './components/usuarios/detail-user/detail-user.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FooterComponent,
    HeaderComponent,
    SidebarComponent,
    ClientsComponent,
    CreateClientComponent,
    SellersComponent,
    CreateSellerComponent,
    BlocksComponent,
    CreateBlockComponent,
    DetailClientComponent,
    IdentificationTypesComponent,
    DetailSellerComponent,
    CreateIdentificationTypeComponent,
    UpdateBlockComponent,
    TerrainsComponent,
    CreateTerrainComponent,
    SalesComponent,
    CreateSaleComponent,
    Step1Component,
    Step2Component,
    Step3Component,
    CustomerSearchingComponent,
    SellerSearchingComponent,
    UsuariosComponent,
    LoginComponent,
    CreateUserComponent,
    PaymentsComponent,
    CreatePaymentComponent,
    ItemsComponent,
    CreateItemComponent,
    UpdateItemComponent,
    CreateCustomerComponent,
    PrincipalComponent,
    DetailUserComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    routing
  ],
  providers: [
    appRoutingProviders,
    { provide: LOCALE_ID, useValue: 'es' },
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
