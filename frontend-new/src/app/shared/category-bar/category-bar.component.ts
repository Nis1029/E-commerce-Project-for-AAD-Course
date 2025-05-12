import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-category-bar',
  standalone:false,
  templateUrl: './category-bar.component.html',
  styleUrls: ['./category-bar.component.css']
})
export class CategoryBarComponent implements OnInit {
  categories: string[] = ['Clothing', 'Shoes', 'Accessories'];
  activeCategory: string | null = null;

  subcategories: { [key: string]: string[] } = {
    'Clothing': ['Dresses', 'T-Shirts', 'Jackets'],
    'Shoes': ['Sneakers', 'Heels'],
    'Accessories': ['Bags', 'Hats']
  };

  constructor() {}

  ngOnInit(): void {}

  formatCategory(category: string): string {
    return category.toLowerCase().replace(/ /g, '-');
  }

  showSubMenu(category: string) {
    this.activeCategory = category;
  }

  hideSubMenu() {
    this.activeCategory = null;
  }

  getSubcategories(category: string): string[] {
    return this.subcategories[category] || [];
  }
}
