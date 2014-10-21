'use strict';

/**
 * @ngdoc overview
 * @name bbsAppApp
 * @description
 * # bbsAppApp
 *
 * Main module of the application.
 */
angular
  .module('playbbs', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ngDialog'
  ])
  .config(function ($routeProvider) {
      $routeProvider
          /*
          .when('/', {
            templateUrl: 'views/main.html',
            controller: 'MainCtrl'
          })
          */
          .otherwise({
            redirectTo: '/'
          });
  }).config(function ($httpProvider) {
      $httpProvider.defaults.transformRequest = function(data) {
          if(data === undefined) {
              return data;
          }
          return $.param(data);
      }
      $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
  });
