import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  template: `
    <section class="dashboard">
      <h2>Dashboard</h2>
      <p>Patient overview and vital signs monitoring</p>
    </section>
  `,
  styles: [
    `
      .dashboard {
        padding: 1rem;
      }
    `,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DashboardComponent {}
