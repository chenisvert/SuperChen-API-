# imgSlider
canvas滑动验证码


### 用法：
1. 引入[imgSlider.min.js](https://github.com/caishuaijun/ImageSlider/blob/master/dist/imgSlider.min.js)

2.
```
imgSlider.init({
  el: document.getElementById('container'),
  onSuccess: function () { ... },
  onFail: function () { ... },
  onRefresh: function () { ... }
})
```

### Tips：
.
1. 图片从 https://picsum.photos/ 随机获取，然后用canvas裁剪生成滑块.

2. 支持移动端使用.

3. 纯前端验证不能保证安全性，本项目仅供学习交流.
