(function() {
  // 图片切换功能
  function changeImage(src) {
    $('#main-product-image').attr('src', src);
  }

  // 动态渲染商品信息
  function renderProduct(commodity) {
    $('#comName').text(commodity.comName);
    $('#comPrice').text(`¥${commodity.comPrice.toFixed(2)}`);
    $('#comDescription').text(commodity.description || '暂无商品描述');
    $('#main-product-image').attr('src', commodity.comPicture);

    // 动态生成缩略图
    const thumbnailContainer = $('.thumbnail-images');
    thumbnailContainer.empty();
    commodity.images?.forEach(img => {
      thumbnailContainer.append(
          `<img class="thumbnail" src="${img}" 
             alt="商品缩略图" 
             onclick="changeImage('${img}')">`
      );
    });

    // 更新商品详情
    $('.product-details .details-content').html(`
      <p>商品名称：${commodity.comName}</p>
      <p>商品编号：${commodity.comId}</p>
      <p>库存数量：${commodity.stock || '充足'}</p>
    `);
  }

  // 获取URL参数
  function getQueryParam(name) {
    const params = new URLSearchParams(location.search);
    return params.get(name);
  }

  // 显示错误信息
  function showError(message) {
    $('.error-message').text(message).show().delay(3000).fadeOut();
  }

  // 页面初始化
  $(document).ready(async function() {
    // 加入购物车功能
    $('#addToCart').click(function() {
      const comId = $('#comId').val();
      const quantity = $('#quantity').val();

      Swal.fire({
        title: '正在加入购物车',
        text: '请稍候...',
        allowOutsideClick: false,
        didOpen: () => Swal.showLoading()
      });

      $.ajax({
        url: '/cart/add',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
          comId: parseInt(comId),
          quantity: parseInt(quantity)
        }),
        success: function(response) {
          Swal.close();
          if (response.success) {
            Swal.fire({
              icon: 'success',
              title: '成功加入购物车!',
              showConfirmButton: false,
              timer: 1500
            });
          } else {
            Swal.fire('失败', response.message || '操作失败', 'error');
          }
        },
        error: function(xhr) {
          Swal.close();
          if (xhr.status === 401) {
            window.location.href = '/login';
          } else {
            Swal.fire('错误', '服务器连接异常，请稍后再试', 'error');
          }
        }
      });
    });

    // 立即购买功能（仅弹窗）
    $('#buyNow').click(function() {
      const price = parseFloat($('.product-price').text().replace('¥', ''));
      const quantity = $('#quantity').val();
      const total = (price * quantity).toFixed(2);

      Swal.fire({
        title: '订单确认',
        html: `
          <div class="order-summary">
            <h4>${$('.product-title').text()}</h4>
            <p>单价: ¥${price.toFixed(2)}</p>
            <p>数量: ${quantity}</p>
            <hr>
            <h5 class="total-amount">总计: ¥${total}</h5>
          </div>
        `,
        confirmButtonText: '确认支付',
        showCancelButton: true,
        cancelButtonText: '取消'
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire({
            icon: 'success',
            title: '支付成功!',
            html: `<p>支付金额: ¥${total}</p>`,
            confirmButtonText: '完成'
          });
        }
      });
    });

    // 加载商品数据
    try {
      const comId = getQueryParam('comId');
      if (!comId) throw new Error('商品参数错误');

      const commodity = await $.get(`/commodity/${comId}`);
      renderProduct(commodity);
    } catch (error) {
      showError('商品加载失败，请刷新重试');
      console.error('商品加载错误:', error);
    }
  });
})();