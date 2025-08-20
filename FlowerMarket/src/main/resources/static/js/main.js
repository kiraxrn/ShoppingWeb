document.addEventListener('DOMContentLoaded', function() {
    const images = document.querySelectorAll('#group3_1 .carousel-image');
    const dotsContainer = document.createElement('div');
    dotsContainer.className = 'carousel-controls';
    document.getElementById('group3_1').appendChild(dotsContainer);

    let currentIndex = 0;
    let interval;

    // 创建指示点
    images.forEach((_, index) => {
        const dot = document.createElement('span');
        dot.className = 'dot';
        if (index === 0) dot.classList.add('active');
        dotsContainer.appendChild(dot);
    });

    // 初始化轮播图
    function initCarousel() {
        images.forEach((image, index) => {
            if (index === 0) {
                image.classList.add('active');
            } else {
                image.classList.remove('active');
            }
        });
    }

    // 更新轮播图
    function updateCarousel() {
        images.forEach(image => image.classList.remove('active'));
        images[currentIndex].classList.add('active');

        const dots = document.querySelectorAll('.dot');
        dots.forEach(dot => dot.classList.remove('active'));
        dots[currentIndex].classList.add('active');
    }

    // 下一张
    function nextSlide() {
        currentIndex = (currentIndex + 1) % images.length;
        updateCarousel();
    }

    // 自动轮播
    function startInterval() {
        interval = setInterval(nextSlide, 3000); // 3秒切换一次
    }

    // 初始化
    initCarousel();
    startInterval();

    // 鼠标悬停时暂停轮播
    document.getElementById('group3_1').addEventListener('mouseenter', () => {
        clearInterval(interval);
    });

    // 鼠标离开时继续轮播
    document.getElementById('group3_1').addEventListener('mouseleave', () => {
        startInterval();
    });
});
