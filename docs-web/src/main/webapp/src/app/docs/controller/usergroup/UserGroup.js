'use strict';

/**
 * User/group controller.
 */
angular.module('docs').controller('UserGroup', function(Restangular, $scope, $state, User) {
  // Load user info
  $scope.selectedUser = null;
  User.userInfo().then(function(data) {
    $scope.userInfo = data;
    // 默认选中 admin
    var adminUser = { username: 'admin' };
    $scope.selectedUser = adminUser;
  });

  // Load users
  Restangular.one('user/list').get({
    sort_column: 1,
    asc: true
  }).then(function(data) {
    $scope.users = data.users;
  });

  // Load groups
  Restangular.one('group').get({
    sort_column: 1,
    asc: true
  }).then(function(data) {
    $scope.groups = data.groups;
  });

  // Load registration requests
  Restangular.one('user').one('register').get().then(function(data) {
    // Process the requests to extract username and email
    $scope.pendingRequests = data.requests.map(function(request) {
      try {
        return {
          id: request.id,
          username: request.username || '',
          email: request.email || '',
          type: request.type,
          createDate: request.createDate
        };
      } catch (e) {
        return {
          id: request.id,
          username: '',
          email: '',
          type: request.type,
          createDate: request.createDate
        };
      }
    });
  }).catch(function(error) {
    console.error('Failed to load registration requests:', error);
  });

  // Approve a registration request
  $scope.approveRequest = function(requestId) {
    // Check if user has admin privileges
    if (!$scope.userInfo || !$scope.userInfo.base_functions || $scope.userInfo.base_functions.indexOf('ADMIN') === -1) {
      alert('您没有管理员权限，无法批准注册申请。请使用管理员账号登录。');
      return;
    }
    Restangular.one('user').one('register', requestId).post('accept').then(function(response) {
      alert('申请已批准');
      // Optionally, you can re-fetch the registration requests to update the UI
      $scope.reloadRequests();
    }).catch(function(error) {
      console.error('批准失败:', error);
      if (error.status === 403) {
        alert('您没有管理员权限，无法批准注册申请。请使用管理员账号登录。');
      } else {
        alert('批准失败: ' + (error.data && error.data.message ? error.data.message : '未知错误'));
      }
    });
  };

  // Reject a registration request
  $scope.rejectRequest = function(requestId) {
    // Check if user has admin privileges
    if (!$scope.userInfo || !$scope.userInfo.base_functions || $scope.userInfo.base_functions.indexOf('ADMIN') === -1) {
      alert('您没有管理员权限，无法拒绝注册申请。请使用管理员账号登录。');
      return;
    }
    
    Restangular.one('user').one('register', requestId).post('reject').then(function(response) {
      alert('申请已拒绝');
      // Optionally, you can re-fetch the registration requests to update the UI
      $scope.reloadRequests();
    }).catch(function(error) {
      console.error('拒绝失败:', error);
      if (error.status === 403) {
        alert('您没有管理员权限，无法拒绝注册申请。请使用管理员账号登录。');
      } else {
        alert('拒绝失败: ' + (error.data && error.data.message ? error.data.message : '未知错误'));
      }
    });
  };

  // Reload registration requests
  $scope.reloadRequests = function() {
    Restangular.one('user').one('register').get().then(function(data) {
      $scope.pendingRequests = data.requests.map(function(request) {
        try {
          return {
            id: request.id,
            username: request.username || '',
            email: request.email || '',
            type: request.type,
            createDate: request.createDate
          };
        } catch (e) {
          return {
            id: request.id,
            username: '',
            email: '',
            type: request.type,
            createDate: request.createDate
          };
        }
      });
    }).catch(function(error) {
      console.error('Failed to reload registration requests:', error);
    });
  };

  // Open a user
  $scope.openUser = function(user) {
    $scope.selectedUser = user;
    $state.go('user.profile', { username: user.username });
  };

  // Open a group
  $scope.openGroup = function(group) {
    $state.go('group.profile', { name: group.name });
  };
});
