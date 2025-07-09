import {Module} from '@nestjs/common';
import {AppController} from './app.controller';
import {AppService} from './app.service';
import {ConfigModule} from "@nestjs/config";
import * as fs from "node:fs";
import * as path from "node:path";
import * as process from "node:process";
import {homedir} from 'os'

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: [
        fs.existsSync(path.join(__dirname, `../config/.env.${process.env.NODE_ENV || 'local'}`)) ? path.join(__dirname, `../config/.env.${process.env.NODE_ENV || 'local'}`) : null,
        fs.existsSync(path.join(homedir(), '/.env.local')) ? path.join(homedir(), '/.env.local') : null,
      ]
    })
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {
}
