import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NiceDatetimeFormatPipe } from './nice-datetime-format.pipe';



@NgModule({
    declarations: [
        NiceDatetimeFormatPipe
    ],
    exports: [
        NiceDatetimeFormatPipe
    ],
    imports: [
        CommonModule
    ]
})
export class NiceDatetimeFormatModule { }
