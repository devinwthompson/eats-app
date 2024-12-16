class RestaurantCard {
    constructor(restaurant, onSwipe) {
        this.restaurant = restaurant;
        this.onSwipe = onSwipe;
        this.element = this.createCard();
    }

    createCard() {
        const card = document.createElement('div');
        card.className = 'restaurant-card';
        card.innerHTML = `
            <img src="${this.restaurant.imageUrl}" alt="${this.restaurant.name}">
            <div class="card-content">
                <h2>${this.restaurant.name}</h2>
                <div class="rating">★ ${this.restaurant.rating}</div>
                <p class="cuisine">${this.restaurant.cuisine}</p>
                <p class="price">${this.restaurant.priceRange}</p>
                <p class="description">${this.restaurant.description}</p>
            </div>
        `;
        this.setupSwipeHandlers(card);
        return card;
    }

    setupSwipeHandlers(card) {
        let startX;
        card.addEventListener('touchstart', e => startX = e.touches[0].clientX);
        card.addEventListener('touchend', e => {
            const endX = e.changedTouches[0].clientX;
            const diff = endX - startX;
            if (Math.abs(diff) > 50) {
                this.onSwipe(diff > 0 ? 'right' : 'left');
            }
        });
    }
} 