import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { appRoutingProviders, routing } from './app.routing';

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
    Step3Component
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    routing
  ],
  providers: [
    appRoutingProviders
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
