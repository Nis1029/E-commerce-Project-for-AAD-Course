export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  imageUrl: string;
  category: string;
  gender: string;
  images?: string[];
  stock: number;
}
