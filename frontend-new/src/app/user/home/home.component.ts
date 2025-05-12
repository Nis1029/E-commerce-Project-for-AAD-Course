import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  standalone:false,
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  featuredImage = 'assets/products/images/Screenshot 2025-05-09 at 21.39.41.png';

  trendingItems = [
    { title: 'Trending colours', image: 'assets/products/images/Screenshot 2025-05-09 at 21.46.49.png' },
    { title: 'The night out edit', image: 'assets/products/images/Screenshot 2025-05-09 at 21.46.20.png' },
    { title: 'Graphic tees', image: 'assets/products/images/Screenshot 2025-05-09 at 21.45.45.png' },
    { title: 'Accessories', image: 'assets/products/images/Screenshot 2025-05-09 at 21.45.05.png' },


  ];
}
