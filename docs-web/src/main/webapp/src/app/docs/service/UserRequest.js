'use strict';

/**
 * UserRequest service.
 */
angular.module('docs').factory('UserRequest', function(Restangular) {
    return {
        /**
         * 提交用户请求（例如注册）
         * @param request 用户请求数据
         */
        submitUserRequest: function(request) {
            return Restangular.all('register').post(request);
        },

        /**
         * 获取所有用户请求
         */
        getAllUserRequests: function() {
            return Restangular.all('register').getList();
        },

        /**
         * 同意某个请求
         * @param requestId 请求 ID
         */
        approveUserRequest: function(requestId) {
            return Restangular.one('register', requestId).post('accept');
        },

        /**
         * 拒绝某个请求
         * @param requestId 请求 ID
         */
        denyUserRequest: function(requestId) {
            return Restangular.one('register', requestId).post('reject');
        }
    };
});
