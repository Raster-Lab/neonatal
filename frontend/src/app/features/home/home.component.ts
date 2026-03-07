import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-home',
  standalone: true,
  template: `
    <section class="home">
      <h2>Welcome to the NICU Management System</h2>
      <p>Neonatal Intensive Care Unit — Patient Monitoring & Management</p>
    </section>
  `,
  styles: [
    `
      .home {
        padding: 1rem;
      }
    `,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomeComponent {}
