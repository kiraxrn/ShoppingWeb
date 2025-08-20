  // 模拟订单数据
  const orders = [
    {
        id: "ORD1001",
        date: "2025-02-10",
        status: "paid",
        items: [
            { name: "商品1", price: 19.99, quantity: 2 },
            { name: "商品3", price: 39.99, quantity: 1 }
        ],
        total: 79.97
    },
    {
        id: "ORD1002",
        date: "2025-02-05",
        status: "delivered",
        items: [
            { name: "商品2", price: 29.99, quantity: 1 }
        ],
        total: 29.99
    },
    {
        id: "ORD1003",
        date: "2025-02-01",
        status: "pending",
        items: [
            { name: "商品4", price: 49.99, quantity: 1 },
            { name: "商品5", price: 59.99, quantity: 1 }
        ],
        total: 109.98
    }
];

// 渲染订单列表
function renderOrders() {
    const orderListElement = document.getElementById('order-list');
    orderListElement.innerHTML = '';

    if (orders.length === 0) {
        orderListElement.innerHTML = '<div class="empty-orders">暂无订单</div>';
        return;
    }

    orders.forEach(order => {
        const orderElement = document.createElement('div');
        orderElement.className = 'order';

        let statusClass = '';
        let statusText = '';

        switch (order.status) {
            case 'pending':
                statusClass = 'status-pending';
                statusText = '待付款';
                break;
            case 'paid':
                statusClass = 'status-paid';
                statusText = '已付款';
                break;
            case 'delivered':
                statusClass = 'status-delivered';
                statusText = '已发货';
                break;
            default:
                statusText = '未知状态';
        }

        orderElement.innerHTML = `
            <div class="order-header">
                <div class="order-id">订单号: ${order.id}</div>
                <div class="order-date">下单时间: ${order.date}</div>
                <div class="order-status ${statusClass}">${statusText}</div>
            </div>
            <div class="order-items">
                ${order.items.map(item => `
                    <div class="order-item">
                        <span class="order-item-name">${item.name} x${item.quantity}</span>
                        <span class="order-item-price">¥${(item.price * item.quantity).toFixed(2)}</span>
                    </div>
                `).join('')}
            </div>
            <div class="order-total">总计: ¥${order.total.toFixed(2)}</div>
        `;
        orderListElement.appendChild(orderElement);
    });
}

// 初始化订单列表渲染
renderOrders();