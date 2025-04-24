'use strict';

/**
 * Login controller.
 */
angular.module('docs').controller('Login', function(Restangular, $scope, $rootScope, $state, $stateParams, $dialog, User, $translate, $uibModal) {
  $scope.codeRequired = false;
  $scope.guset_login = true;

  // Get the app configuration
  Restangular.one('app').get().then(function(data) {
    $rootScope.app = data;
  });

  // Login as guest
  $scope.loginAsGuest = function() {
    $scope.user = {
      username: 'guest',
      password: ''
    };
    $scope.login();
  };
  
  // Login
  $scope.login = function() {
    User.login($scope.user).then(function() {
      User.userInfo(true).then(function(data) {
        $rootScope.userInfo = data;
      });

      if($stateParams.redirectState !== undefined && $stateParams.redirectParams !== undefined) {
        $state.go($stateParams.redirectState, JSON.parse($stateParams.redirectParams))
          .catch(function() {
            $state.go('document.default');
          });
      } else {
        $state.go('document.default');
      }
    }, function(data) {
      if (data.data.type === 'ValidationCodeRequired') {
        // A TOTP validation code is required to login
        $scope.codeRequired = true;
      } else {
        // Login truly failed
        var title = $translate.instant('login.login_failed_title');
        var msg = $translate.instant('login.login_failed_message');
        var btns = [{result: 'ok', label: $translate.instant('ok'), cssClass: 'btn-primary'}];
        $dialog.messageBox(title, msg, btns);
      }
    });
  };

  // Password lost
  $scope.openPasswordLost = function () {
    $uibModal.open({
      templateUrl: 'partial/docs/passwordlost.html',
      controller: 'ModalPasswordLost'
    }).result.then(function (username) {
      if (username === null) {
        return;
      }

      // Send a password lost email
      Restangular.one('user').post('password_lost', {
        username: username
      }).then(function () {
        var title = $translate.instant('login.password_lost_sent_title');
        var msg = $translate.instant('login.password_lost_sent_message', { username: username });
        var btns = [{result: 'ok', label: $translate.instant('ok'), cssClass: 'btn-primary'}];
        $dialog.messageBox(title, msg, btns);
      }, function () {
        var title = $translate.instant('login.password_lost_error_title');
        var msg = $translate.instant('login.password_lost_error_message');
        var btns = [{result: 'ok', label: $translate.instant('ok'), cssClass: 'btn-primary'}];
        $dialog.messageBox(title, msg, btns);
      });
    });
  };

  $scope.openRegisterModal = function() {
    $uibModal.open({
      templateUrl: 'partial/docs/register.html',
      controller: [
        '$scope',
        '$uibModalInstance',
        '$http',
        function($scope, $uibModalInstance, $http) {
          // 初始化注册数据对象
          $scope.registerData = {};

          // 提交注册表单
          $scope.submitRegister = function() {
            if (!$scope.registerData.storage_quota) {
              $scope.registerData.storage_quota = 8192;
            }
            $http({
              method: 'PUT',
              url: '/docs-web/api/user/register',
              data: $.param($scope.registerData),
              headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
              }
            }).then(function success(response) {
              alert('注册申请已提交，请等待管理员审批');
              $uibModalInstance.close();
            }, function error(error) {
              console.error('注册失败:', error);
              alert('注册失败，请稍后重试');
            });
          };

          // 取消注册弹窗
          $scope.cancel = function() {
            $uibModalInstance.dismiss('cancel');
          };
        }
      ]
    });
  };


});