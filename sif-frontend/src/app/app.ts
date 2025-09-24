import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navbar } from './layout/navbar/navbar'; 
import { CommonModule } from '@angular/common'; 
import { AuthService } from './services/auth';

@Component({
  selector: 'app-root',
  standalone: true, // <-- INDICA QUE É UM COMPONENTE AUTÔNOMO
  imports: [RouterOutlet,  Navbar, CommonModule ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('sif-frontend');
  constructor(public authService: AuthService) {}
}
