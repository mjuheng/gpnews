(global["webpackJsonp"] = global["webpackJsonp"] || []).push([["components/image-cutter/image-cutter"],{

/***/ 195:
/*!********************************************************************************!*\
  !*** F:/Intel/gpnews/gpnews_consumer/components/image-cutter/image-cutter.vue ***!
  \********************************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _image_cutter_vue_vue_type_template_id_768439d9_scoped_true___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./image-cutter.vue?vue&type=template&id=768439d9&scoped=true& */ 196);
/* harmony import */ var _image_cutter_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./image-cutter.vue?vue&type=script&lang=js& */ 198);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _image_cutter_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _image_cutter_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__[key]; }) }(__WEBPACK_IMPORT_KEY__));
/* harmony import */ var _image_cutter_vue_vue_type_style_index_0_id_768439d9_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./image-cutter.vue?vue&type=style&index=0&id=768439d9&scoped=true&lang=css& */ 200);
/* harmony import */ var _E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/runtime/componentNormalizer.js */ 14);

var renderjs





/* normalize component */

var component = Object(_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_3__["default"])(
  _image_cutter_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__["default"],
  _image_cutter_vue_vue_type_template_id_768439d9_scoped_true___WEBPACK_IMPORTED_MODULE_0__["render"],
  _image_cutter_vue_vue_type_template_id_768439d9_scoped_true___WEBPACK_IMPORTED_MODULE_0__["staticRenderFns"],
  false,
  null,
  "768439d9",
  null,
  false,
  _image_cutter_vue_vue_type_template_id_768439d9_scoped_true___WEBPACK_IMPORTED_MODULE_0__["components"],
  renderjs
)

/* hot reload */
if (false) { var api; }
component.options.__file = "F:/Intel/gpnews/gpnews_consumer/components/image-cutter/image-cutter.vue"
/* harmony default export */ __webpack_exports__["default"] = (component.exports);

/***/ }),

/***/ 196:
/*!***************************************************************************************************************************!*\
  !*** F:/Intel/gpnews/gpnews_consumer/components/image-cutter/image-cutter.vue?vue&type=template&id=768439d9&scoped=true& ***!
  \***************************************************************************************************************************/
/*! exports provided: render, staticRenderFns, recyclableRender, components */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_image_cutter_vue_vue_type_template_id_768439d9_scoped_true___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--16-0!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/template.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-uni-app-loader/page-meta.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./image-cutter.vue?vue&type=template&id=768439d9&scoped=true& */ 197);
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "render", function() { return _E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_image_cutter_vue_vue_type_template_id_768439d9_scoped_true___WEBPACK_IMPORTED_MODULE_0__["render"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "staticRenderFns", function() { return _E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_image_cutter_vue_vue_type_template_id_768439d9_scoped_true___WEBPACK_IMPORTED_MODULE_0__["staticRenderFns"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "recyclableRender", function() { return _E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_image_cutter_vue_vue_type_template_id_768439d9_scoped_true___WEBPACK_IMPORTED_MODULE_0__["recyclableRender"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "components", function() { return _E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_image_cutter_vue_vue_type_template_id_768439d9_scoped_true___WEBPACK_IMPORTED_MODULE_0__["components"]; });



/***/ }),

/***/ 197:
/*!***************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--16-0!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/template.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-uni-app-loader/page-meta.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!F:/Intel/gpnews/gpnews_consumer/components/image-cutter/image-cutter.vue?vue&type=template&id=768439d9&scoped=true& ***!
  \***************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! exports provided: render, staticRenderFns, recyclableRender, components */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "render", function() { return render; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "staticRenderFns", function() { return staticRenderFns; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "recyclableRender", function() { return recyclableRender; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "components", function() { return components; });
var components
var render = function() {
  var _vm = this
  var _h = _vm.$createElement
  var _c = _vm._self._c || _h
}
var recyclableRender = false
var staticRenderFns = []
render._withStripped = true



/***/ }),

/***/ 198:
/*!*********************************************************************************************************!*\
  !*** F:/Intel/gpnews/gpnews_consumer/components/image-cutter/image-cutter.vue?vue&type=script&lang=js& ***!
  \*********************************************************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _E_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_image_cutter_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!./node_modules/babel-loader/lib!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--12-1!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/script.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./image-cutter.vue?vue&type=script&lang=js& */ 199);
/* harmony import */ var _E_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_image_cutter_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_E_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_image_cutter_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _E_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_image_cutter_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _E_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_image_cutter_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__[key]; }) }(__WEBPACK_IMPORT_KEY__));
 /* harmony default export */ __webpack_exports__["default"] = (_E_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_image_cutter_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0___default.a); 

/***/ }),

/***/ 199:
/*!****************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/babel-loader/lib!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--12-1!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/script.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!F:/Intel/gpnews/gpnews_consumer/components/image-cutter/image-cutter.vue?vue&type=script&lang=js& ***!
  \****************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(uni) {Object.defineProperty(exports, "__esModule", { value: true });exports.default = void 0; //
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
var _default =
{
  props: {
    url: {
      type: String,
      default: "" },

    fixed: {
      type: Boolean,
      default: false },

    width: {
      type: Number,
      default: 200 },

    height: {
      type: Number,
      default: 200 },

    maxWidth: {
      type: Number,
      default: 1024 },

    maxHeight: {
      type: Number,
      default: 1024 },

    blob: {
      type: Boolean,
      default: true } },


  data: function data() {
    return {
      mask: {
        show: false },

      frame: {
        left: 50,
        top: 50,
        width: this.width,
        height: this.height },

      image: {
        left: 20,
        top: 20,
        width: 300,
        height: 400 },

      real: {
        width: 100,
        height: 100 },

      target: {
        width: this.width,
        height: this.height },

      touches: [],
      type: "",
      start: {
        frame: {
          left: 0,
          top: 0,
          width: 0,
          height: 0 },

        image: {
          left: 0,
          top: 0,
          width: 0,
          height: 0 } },


      timeoutId: -1,
      context: null };

  },
  mounted: function mounted() {





    this.context = uni.createCanvasContext("canvas", this);
    this.targetContext = uni.createCanvasContext("target", this);
  },
  methods: {
    imageLoad: function imageLoad(ev) {var _this = this;
      this.mask.show = true;
      this.real.width = ev.detail.width;
      this.real.height = ev.detail.height;
      this.image.width = ev.detail.width;
      this.image.height = ev.detail.height;
      this.frame.width = this.width;
      this.frame.height = this.height;
      if (!this.fixed) {
        this.frame.width = this.image.width;
        this.frame.height = this.image.height;
      }
      var query = uni.createSelectorQuery().in(this);
      query.select(".body").boundingClientRect(function (data) {
        var bw = data.width;
        var bh = data.height;
        var fw = _this.frame.width;
        var fh = _this.frame.height;
        var tw = bw * 0.8;
        var th = bh * 0.8;
        var sx = tw / fw;
        var sy = th / fh;
        var scale = sx;
        if (sx < sy) {
          scale = sy;
        }
        tw = fw * scale;
        th = fh * scale;
        var tx = (bw - tw) / 2;
        var ty = (bh - th) / 2;
        _this.frame.width = tw;
        _this.frame.height = th;
        _this.frame.left = tx;
        _this.frame.top = ty;

        var iw = _this.image.width;
        var ih = _this.image.height;
        sx = tw / iw;
        sy = th / ih;
        scale = sx;
        if (sx < sy) {
          scale = sy;
        }
        _this.image.width = iw * scale;
        _this.image.height = ih * scale;
        _this.image.left = (bw - _this.image.width) / 2;
        _this.image.top = (bh - _this.image.height) / 2;
        setTimeout(function () {
          _this.trimImage();
        }, 100);
      }).exec();
    },
    touchHandle: function touchHandle() {},
    touchStart: function touchStart(ev, type) {
      this.stopTime();
      this.mask.show = false;
      if (this.touches.length == 0) {
        this.type = type;
        this.start.frame.left = this.frame.left;
        this.start.frame.top = this.frame.top;
        this.start.frame.width = this.frame.width;
        this.start.frame.height = this.frame.height;
        this.start.image.left = this.image.left;
        this.start.image.top = this.image.top;
        this.start.image.width = this.image.width;
        this.start.image.height = this.image.height;
      }
      var touches = ev.changedTouches;
      for (var i = 0; i < touches.length; i++) {
        var touch = touches[i];
        // this.touches[touch.identifier] = touch;
        this.touches.push(touch);
      }
    },
    touchMove: function touchMove(ev) {
      this.stopTime();
      ev.preventDefault();
      var touches = ev.touches;
      if (this.touches.length == 1) {
        if (this.type == "plank" || this.type == "frame" || this.fixed) {
          this.moveImage(this.touches[0], touches[0]);
        } else {
          this.scaleFrame(this.touches[0], touches[0], this.type);
        }
      } else if (this.touches.length == 2 && touches.length == 2) {
        var ta = this.touches[0];
        var tb = this.touches[1];
        var tc = touches[0];
        var td = touches[1];
        if (ta.identifier != tc.identifier) {
          var temp = tc;
          tc = td;
          td = temp;
        }
        this.scaleImage(ta, tb, tc, td);
      }
    },
    touchEnd: function touchEnd(ev) {
      this.type = "";
      this.touches = [];
      this.startTime();
    },
    touchCancel: function touchCancel(ev) {
      this.type = "";
      this.touches = [];
      this.startTime();
    },
    startTime: function startTime() {var _this2 = this;
      this.stopTime();
      this.timeoutId = setTimeout(function () {
        _this2.trimImage();
      }, 800);
    },
    stopTime: function stopTime() {
      if (this.timeoutId >= 0) {
        clearTimeout(this.timeoutId);
        this.timeoutId = -1;
      }
    },
    trimImage: function trimImage() {var _this3 = this;
      this.mask.show = true;
      var query = uni.createSelectorQuery().in(this);
      query.select(".body").boundingClientRect(function (data) {
        var bw = data.width;
        var bh = data.height;
        var fw = _this3.frame.width;
        var fh = _this3.frame.height;
        var tw = bw * 0.8;
        var th = bh * 0.8;
        var sx = tw / fw;
        var sy = th / fh;
        var scale = sx;
        if (sx > sy) {
          scale = sy;
        }
        tw = fw * scale;
        th = fh * scale;
        var tx = (bw - tw) / 2;
        var ty = (bh - th) / 2;
        var ax = tx - _this3.frame.left + (_this3.frame.left - _this3.image.left) * (1 - scale);
        var ay = ty - _this3.frame.top + (_this3.frame.top - _this3.image.top) * (1 - scale);
        _this3.frame.width = tw;
        _this3.frame.height = th;
        _this3.frame.left = tx;
        _this3.frame.top = ty;
        _this3.image.width *= scale;
        _this3.image.height *= scale;
        _this3.image.left += ax;
        _this3.image.top += ay;
      }).exec();
      setTimeout(function () {
        var scale = _this3.image.width / _this3.real.width;
        var x = (_this3.frame.left - _this3.image.left) / scale;
        var y = (_this3.frame.top - _this3.image.top) / scale;
        var width = _this3.frame.width / scale;
        var height = _this3.frame.height / scale;
        _this3.context.drawImage(_this3.url, x, y, width, height, 0, 0, _this3.frame.width, _this3.frame.height);
        _this3.context.draw(false);
      }, 100);
    },
    moveImage: function moveImage(ta, tb) {
      var ax = tb.clientX - ta.clientX;
      var ay = tb.clientY - ta.clientY;
      this.image.left = this.start.image.left + ax;
      this.image.top = this.start.image.top + ay;
      if (this.image.left > this.frame.left) {
        this.image.left = this.frame.left;
      }
      if (this.image.top > this.frame.top) {
        this.image.top = this.frame.top;
      }
      if (this.image.left + this.image.width < this.frame.left + this.frame.width) {
        this.image.left = this.frame.left + this.frame.width - this.image.width;
      }
      if (this.image.top + this.image.height < this.frame.top + this.frame.height) {
        this.image.top = this.frame.top + this.frame.height - this.image.height;
      }
    },
    scaleImage: function scaleImage(ta, tb, tc, td) {
      var x1 = ta.clientX;
      var y1 = ta.clientY;
      var x2 = tb.clientX;
      var y2 = tb.clientY;
      var x3 = tc.clientX;
      var y3 = tc.clientY;
      var x4 = td.clientX;
      var y4 = td.clientY;
      var ol = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
      var el = Math.sqrt((x3 - x4) * (x3 - x4) + (y3 - y4) * (y3 - y4));
      var ocx = (x1 + x2) / 2;
      var ocy = (y1 + y2) / 2;
      var ecx = (x3 + x4) / 2;
      var ecy = (y3 + y4) / 2;
      var ax = ecx - ocx;
      var ay = ecy - ocy;
      var scale = el / ol;
      if (this.start.image.width * scale < this.frame.width) {
        scale = this.frame.width / this.start.image.width;
      }
      if (this.start.image.height * scale < this.frame.height) {
        scale = this.frame.height / this.start.image.height;
      }
      if (this.start.image.width * scale < this.frame.width) {
        scale = this.frame.width / this.start.image.width;
      }
      this.image.left = this.start.image.left + ax - (ocx - this.start.image.left) * (scale - 1);
      this.image.top = this.start.image.top + ay - (ocy - this.start.image.top) * (scale - 1);
      this.image.width = this.start.image.width * scale;
      this.image.height = this.start.image.height * scale;
      if (this.image.left > this.frame.left) {
        this.image.left = this.frame.left;
      }
      if (this.image.top > this.frame.top) {
        this.image.top = this.frame.top;
      }
      if (this.image.left + this.image.width < this.frame.left + this.frame.width) {
        this.image.left = this.frame.left + this.frame.width - this.image.width;
      }
      if (this.image.top + this.image.height < this.frame.top + this.frame.height) {
        this.image.top = this.frame.top + this.frame.height - this.image.height;
      }

    },
    scaleFrame: function scaleFrame(ta, tb, type) {
      var ax = tb.clientX - ta.clientX;
      var ay = tb.clientY - ta.clientY;
      var x1 = this.start.frame.left;
      var y1 = this.start.frame.top;
      var x2 = this.start.frame.left + this.start.frame.width;
      var y2 = this.start.frame.top + this.start.frame.height;
      if (type == "left") {
        x1 += ax;
      } else if (type == "right") {
        x2 += ax;
      } else if (type == "top") {
        y1 += ay;
      } else if (type == "bottom") {
        y2 += ay;
      } else if (type == "left-top") {
        x1 += ax;
        y1 += ay;
      } else if (type == "left-bottom") {
        x1 += ax;
        y2 += ay;
      } else if (type == "right-top") {
        x2 += ax;
        y1 += ay;
      } else if (type == "right-bottom") {
        x2 += ax;
        y2 += ay;
      }
      if (x1 < this.image.left) {
        x1 = this.image.left;
      }
      if (y1 < this.image.top) {
        y1 = this.image.top;
      }
      if (x2 > this.image.left + this.image.width) {
        x2 = this.image.left + this.image.width;
      }
      if (y2 > this.image.top + this.image.height) {
        y2 = this.image.top + this.image.height;
      }
      this.frame.left = x1;
      this.frame.top = y1;
      this.frame.width = x2 - x1;
      this.frame.height = y2 - y1;
    },
    parseBlob: function parseBlob(base64) {
      var arr = base64.split(',');
      var mime = arr[0].match(/:(.*?);/)[1];
      var bstr = atob(arr[1]);
      var n = bstr.length;
      var u8arr = new Uint8Array(n);
      for (var i = 0; i < n; i++) {
        u8arr[i] = bstr.charCodeAt(i);
      }
      var url = URL || webkitURL;
      return url.createObjectURL(new Blob([u8arr], { type: mime }));
    },
    onok: function onok() {var _this4 = this;
      var scale = this.image.width / this.real.width;
      var x = (this.frame.left - this.image.left) / scale;
      var y = (this.frame.top - this.image.top) / scale;
      var width = this.frame.width / scale;
      var height = this.frame.height / scale;
      var tw = width;
      var th = height;
      if (this.fixed) {
        tw = this.width / 2;
        th = this.height / 2;
      } else {
        if (tw > this.maxWidth / 2) {
          var sc = this.maxWidth / 2 / tw;
          tw = tw * sc;
          th = th * sc;
        }
        if (th > this.maxHeight / 2) {
          var sc = this.maxHeight / 2 / th;
          th = th * sc;
          tw = tw * sc;
        }
      }
      this.target.width = tw;
      this.target.height = th;
      uni.showLoading({
        title: "正在裁剪" });

      setTimeout(function () {
        _this4.targetContext.drawImage(_this4.url, x, y, width, height, 0, 0, tw, th);
        _this4.targetContext.draw(false, function () {
          uni.canvasToTempFilePath({
            canvasId: "target",
            success: function success(res) {
              var path = res.tempFilePath;





              _this4.$emit("ok", {
                path: path });

            },
            fail: function fail(ev) {
              console.log(ev);
            },
            complete: function complete() {
              uni.hideLoading();
            } },
          _this4);
        });
      }, 100);
    },
    oncancle: function oncancle() {
      this.$emit("cancel");
    } } };exports.default = _default;
/* WEBPACK VAR INJECTION */}.call(this, __webpack_require__(/*! ./node_modules/@dcloudio/uni-mp-weixin/dist/index.js */ 1)["default"]))

/***/ }),

/***/ 200:
/*!*****************************************************************************************************************************************!*\
  !*** F:/Intel/gpnews/gpnews_consumer/components/image-cutter/image-cutter.vue?vue&type=style&index=0&id=768439d9&scoped=true&lang=css& ***!
  \*****************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _E_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_1_E_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_index_js_ref_6_oneOf_1_2_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_E_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_image_cutter_vue_vue_type_style_index_0_id_768439d9_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!./node_modules/mini-css-extract-plugin/dist/loader.js??ref--6-oneOf-1-0!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--6-oneOf-1-1!./node_modules/css-loader??ref--6-oneOf-1-2!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/stylePostLoader.js!./node_modules/postcss-loader/src??ref--6-oneOf-1-3!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./image-cutter.vue?vue&type=style&index=0&id=768439d9&scoped=true&lang=css& */ 201);
/* harmony import */ var _E_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_1_E_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_index_js_ref_6_oneOf_1_2_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_E_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_image_cutter_vue_vue_type_style_index_0_id_768439d9_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_E_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_1_E_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_index_js_ref_6_oneOf_1_2_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_E_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_image_cutter_vue_vue_type_style_index_0_id_768439d9_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0__);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _E_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_1_E_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_index_js_ref_6_oneOf_1_2_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_E_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_image_cutter_vue_vue_type_style_index_0_id_768439d9_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _E_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_1_E_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_index_js_ref_6_oneOf_1_2_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_E_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_image_cutter_vue_vue_type_style_index_0_id_768439d9_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0__[key]; }) }(__WEBPACK_IMPORT_KEY__));
 /* harmony default export */ __webpack_exports__["default"] = (_E_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_1_E_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_index_js_ref_6_oneOf_1_2_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_E_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_image_cutter_vue_vue_type_style_index_0_id_768439d9_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0___default.a); 

/***/ }),

/***/ 201:
/*!*********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/mini-css-extract-plugin/dist/loader.js??ref--6-oneOf-1-0!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--6-oneOf-1-1!./node_modules/css-loader??ref--6-oneOf-1-2!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/stylePostLoader.js!./node_modules/postcss-loader/src??ref--6-oneOf-1-3!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!F:/Intel/gpnews/gpnews_consumer/components/image-cutter/image-cutter.vue?vue&type=style&index=0&id=768439d9&scoped=true&lang=css& ***!
  \*********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// extracted by mini-css-extract-plugin

/***/ })

}]);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/components/image-cutter/image-cutter.js.map
;(global["webpackJsonp"] = global["webpackJsonp"] || []).push([
    'components/image-cutter/image-cutter-create-component',
    {
        'components/image-cutter/image-cutter-create-component':(function(module, exports, __webpack_require__){
            __webpack_require__('1')['createComponent'](__webpack_require__(195))
        })
    },
    [['components/image-cutter/image-cutter-create-component']]
]);
