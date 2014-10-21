/*global define */
'use strict';

/*
 * Doc: https://github.com/likeastore/ngDialog#api
 */
var playbbs = angular.module('playbbs', ['ngDialog']);
playbbs.config(function($httpProvider){
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
});

playbbs.controller('TweetListCtrl', function($scope, $http, ngDialog) {

    var host = "http://localhost:9000"

    $scope.getTweetList = function() {
        // $http.get(host+'/tweetList').success(function (data, status) {
        $http.jsonp(host+'/tweetList?callback=JSON_CALLBACK').success(function (data, status) {
            // console.log("getList: "+status);
            $scope.tweets = data;
        }).error(function(data, status) {
            console.log("error: "+status);
        });

    }
    
    $scope.newTweet = function(body) {
        $http.get(host+'/tweet/'+body).success(function (data, status) {
        // $http.post(host+'/tweet', body).success(function (data, status) {
        
        // console.log('%0',body);
        // console.log('%0',$scope.tweet_body);
        // $http.post(host+'/tweet', {'body': $scope.tweet_body}).success(function(data, status) {
            console.log("new: "+status);
            //console.log('%0',$scope);
            $scope.getTweetList();

        }).error(function(data, status) {
            console.log("error: "+status);
        });
    }
    
    /*
     * POST 用
     * */
    /*
    $scope.newTweet = function() {
        console.log('scope: %0' ,$scope.tweet_body );

        $http({
            method: 'POST',
            url: host+'/tweet',
            //headers: 'Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept',
            headers: { 'Content-Type':'text/plain; charset=utf-8',
                       'Access-Control-Allow-Origin':'*'
            },
            data: $scope.tweet_body
        }).success(function(data, status) {
            console.log("new: "+status);
            //console.log('%0',$scope);
            $scope.getTweetList();

        }).error(function(data, status) {
            console.log("error: "+status);
        });
    }
    */
    
    /* 
    $scope.updateTweet = function(tweetObj) {
        console.log('%0',tweetObj);
        console.log('%0',$scope);
    }
    */

    $scope.updateDialog = function(tweetObj) {

        // http://likeastore.github.io/ngDialog/#
        // http://qiita.com/hkusu/items/7d748b55ba73cc8a3675
        var dialog = ngDialog.open({ 
            template: 'updateDialog',
            scope: $scope,
            data: tweetObj,
            controller: ['$scope', function($scope) {
                // console.log('%0',$scope.$parent);
                var _data = tweetObj;

                $scope.updateTweet = function(_data) {
                    var _id = tweetObj.id;
                    var _body = _data;
                    console.log('%0',tweetObj.id);
                    console.log('%0',_data);

                    $http.get(host+'/tweet/'+_id+'/update/'+_body
                    ).success(function (data, status) {
                        $scope.getTweetList();
                        console.log("update: "+status);

                    }).error(function(data, status) {
                        console.log('error: ' +status);
                    })
                    $scope.closeThisDialog();
                }
            }]

        });
    }
    
    $scope.deleteTweet = function(id) {
        $http.get(host+'/tweet/'+id+'/delete').success(function (data, status) {
        // $http.delete('/tweet/'+id ).success(function(data, status) {
            console.log("delete: "+status);
            $scope.getTweetList();

        }).error(function(data, status) {
            console.log('error: ' +status);
        })
    }
    
    $scope.getTweetList();
});

