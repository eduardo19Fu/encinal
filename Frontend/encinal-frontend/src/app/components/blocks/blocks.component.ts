import { Component, OnInit } from '@angular/core';
import { BlockService } from '../../services/block-service/block.service';
import { Block } from '../../models/block';
import { JqueryConfigs } from 'src/app/utils/jquery-utils';

@Component({
  selector: 'app-blocks',
  templateUrl: './blocks.component.html',
  styles: [
  ]
})
export class BlocksComponent implements OnInit {

  title: string;
  blocks: Block[];
  block: Block;

  jqueryConfigs: JqueryConfigs;

  constructor(
    private blockService: BlockService
  ) {
    this.title = 'Manzanas';
    this.jqueryConfigs = new JqueryConfigs();
    this.block = new Block();
  }

  ngOnInit(): void {
    this.getBlocks();
  }

  getBlocks(): void{
    this.blockService.getBlocks().subscribe(blocks => {
      this.blocks = blocks;
      this.jqueryConfigs.configDataTable('blocks');
      this.jqueryConfigs.configToolTip();
    });
  }

}
