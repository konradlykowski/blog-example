var app = angular.module('iglaWPodrozy', ['ngAnimate', 'ngSanitize','ui.router', 'ui.bootstrap', 'ui.bootstrap.modal']);

app.controller('NavBarController', function ($scope, PostsService, $rootScope) {
  $scope.isNavCollapsed = true;
  $scope.isCollapsed = false;
  $scope.isCollapsedHorizontal = false;
  $scope.search = function(phrase) {
    if(phrase.length > 2) {
        PostsService.loadPosts(0, 	 {"bool": {"should": [{ "match": { "tags": phrase   }},{ "match": { "content": phrase   }},{ "match": { "description": phrase   }}]}});
    } else {
        $rootScope.loadedPostsNumber = 0;
        PostsService.loadPosts($rootScope.loadedPostsNumber,{"match_all" : {}});
    }
  }
});

app.service('PostsService', function($http, $rootScope) {
    this.loadComments = function($scope, postId) {
        var req = {
         method: 'POST',
         url: 'http://localhost:9200/comments/_search',
         headers: {
           'Content-Type': 'application/json'
         },
         data: {
              "query": { "bool": {"should": [{ "match": { "postId" : postId} }]}},
              "sort": { "date": { "order": "desc" }}
          }
        }
        $http(req).then(function(response) {
        console.log(response.data.hits.hits)
            $scope.comments = response.data.hits.hits;
        });
    };
    this.loadPost = function($scope, postId) {
        var req = {
         method: 'GET',
         url: 'http://localhost:9200/posts/post/'+postId,
         headers: {
           'Content-Type': 'application/json'
         }
        }
        $http(req).then(function(response) {
            $scope.content = response.data._source.content;
            $scope.postId = response.data._source.id;
            $scope.tags = response.data._source.tags;
            $scope.title = response.data._source.title;
        });
    };
    this.loadPosts = function(loadedPostsNumber, query) {
        var req = {
         method: 'POST',
         url: 'http://localhost:9200/posts/_search',
         headers: {
           'Content-Type': 'application/json'
         },
         data: {
             "from" : loadedPostsNumber, "size" : 7,
             "query": query,
             "sort": { "date": { "order": "desc" }}
         }
        }
        $http(req).then(function(response) {
            if(response.data.hits.hits == 0) {
                $rootScope.noMorePosts = true;
            }
            else {
                $rootScope.noMorePosts = false;
            }
            if(loadedPostsNumber === 0) {
                $rootScope.posts = [];
            }
            Array.prototype.push.apply($rootScope.posts,response.data.hits.hits);
        });
    }
});

app.config(['$locationProvider','$qProvider', function($locationProvider,$qProvider) {
  $locationProvider.hashPrefix('');
  $qProvider.errorOnUnhandledRejections(false);
}]);

app.config(function($stateProvider, $urlRouterProvider, $locationProvider) {
       $urlRouterProvider.otherwise('/posts/');
       $stateProvider
       .state('about', {
           url: '^/about/',
                resolve: {
               function($stateParams, $state, $uibModal, $rootScope,$transition$) {
                                   var modal = $uibModal.open({
                                        animation: true,
                                        templateUrl: 'about.html',
                                        controller: 'AboutController',
                                        size: 'lg'
                                      });
                                      modal.result.finally(function() {
                                          var result = modal.result.$$state.value;
                                          if(typeof result != 'undefined' && result.hasOwnProperty('query')) {
                                              $state.go('posts.search',result);
                                          } else{
                                          if($transition$.from().name === '') {
                                                  $state.go('posts',$transition$.params('from'));
                                             }else {
                                                  $state.go($transition$.from().name,$transition$.params('from'));
                                             }
                                          }
                                        });
                      }
                    },
               onExit: function($uibModalStack){
                   $uibModalStack.dismissAll();
               }
          })
       .state('posts', {
            url: '^/posts/',
            controller: 'MainController',
       })
       .state('posts.search', {
          url: '^/posts/search/:query',
          controller: 'MainController',
         })
        .state('posts.show', {
        url: '^/posts/show/:postId',
           resolve: {
                    posts: function() { return {id:'marta-w-porto.html', title:'Marta w Porto',category:'podroze',date:Math.random()+'th May 2017', description:'Marta przyjechala do Porto zobaczyc co slychac', content:'CONTENT', commentsCount:'12', tags: 'kokos;kokos2', image:'img/image1.JPG', location: 'Zurich'}},
                    function($stateParams, $state, $uibModal, $rootScope,$transition$) {
                                var modal = $uibModal.open({
                                     animation: true,
                                     templateUrl: 'show-post.html',
                                     controller: 'ShowPostController',
                                     size: 'lg',
                                     resolve: {
                                       postId: function() {
                                         return $stateParams.postId;
                                       }
                                     }
                                   });

                                   modal.result.finally(function() {
                                        var result = modal.result.$$state.value;
                                          if(typeof result != 'undefined' && result.hasOwnProperty('query')) {
                                              $state.go('posts.search',result);
                                          } else{
                                          if($transition$.from().name === '') {
                                                  $state.go('posts',$transition$.params('from'));
                                             }else {
                                                  $state.go($transition$.from().name,$transition$.params('from'));
                                             }
                                          }
                                      });
                   }
                 },
            onExit: function($uibModalStack){
                $uibModalStack.dismissAll();
            }
        })
   })

app.controller('MainController', function($location, $scope, $stateParams, $http, $state, PostsService, $rootScope) {
    var loadPosts = function(loadedPostsNumber) {
        var query = {"match_all" : {}};
        if(typeof $stateParams != 'undefined' && $stateParams.hasOwnProperty('query')) {
            var json = "{\"bool\": {\"should\": [{ \"match\": " + "{\"" +$stateParams["query"].replace(":","\":\"") +"\"}" + " }]}}"
            query = JSON.parse(json)
        }
        PostsService.loadPosts(loadedPostsNumber, query);
    }
    loadPosts($rootScope.loadedPostsNumber=0)
    $rootScope.pageChanged = function() {
      $rootScope.loadedPostsNumber = $rootScope.loadedPostsNumber + 7;
      loadPosts($rootScope.loadedPostsNumber);
      console.log($rootScope.loadedPostsNumber)
    };
});

app.controller('CarouselController', function ($scope) {
    $scope.myInterval = 4000;
    $scope.noWrapSlides = false;
    var slides = $scope.slides = [];
    images = [{path:'img/image1.JPG',desc:'marta w...'},{path:'img/image3.JPG',desc:'marta w...'},{path:'img/image4.JPG',desc:'marta w...'}]
    var len = images.length;
    for (var i = 0; i < len; i++) {
    slides.push({
          image: images[i].path,
          text: images[i].desc,
          id: i
        });
    }
});

app.controller('AboutController', function($scope, $uibModalInstance) {
  $scope.content = "<p>Mam za sobą studia i 4 letni epizod w korporacji. Jestem szczęśliwie zakochana. Mam bzika na punkcie podróży, zdrowego stylu życia… i mody. Obecnie zastanawiam sie nad swoja przeszłością, wyciągam wnioski z poprzednich dokonań i szukam inspiracji na przyszłość. </p><p>Od najmłodszych lat uwielbiałam tworzyć i wymyślać ubrania. Początkowo były to ubranka dla lalek, wówczas pierwszy raz siedziałam za “sterami” starej maszyny do szycia. Później wymyślałam ubrania dla siebie i szyła mi je mama. Te doświadczenia sprawiły, że coraz bardziej zarażałam się pasją tworzenia własnych ubrań, eksperymentowania z tkaninami, dodatkami oraz formą.</p><p>Uwielbiam wyprawy do malutkiego sklepiku z materiałami w moim rodzinnym miasteczku. Za każdym razem gdy zobaczę tkaninę, która mi się spodoba, dokładnie wiem co chciałabym z niej uszyć.</p> <p>Zamierzam sama przerabiać, projektować i szyć nowe ubrania. Chciałabym to wszystko połączyć z podróżami, z których zamierzam czerpać inspiracje do nowych projektów, a także tworzyć ubrania z myślą o przyszłych podróżach.</p><p>Moja ostateczna decyzja o pisaniu bloga zapadła właśnie podczas jednej z takich podróży, była nią wyprawa do Azji. Spacerując po pięknym i magicznym Kioto, będąc na końcu świata poczułam, że warto próbować spełniać swoje marzenia. Jeśli nie zaryzykujesz, nie poświęcisz czasu i nie dasz czegoś od siebie to pewnie się nie uda. Ja zamierzam dać z siebie wszystko.</p><p>Mam masę pomysłów, chęci i energii, aby je realizować. Jeżeli jesteś zainteresowana/y moimi poczynaniami, zapraszam na bloga.";
  $scope.cancel = function() {
    $uibModalInstance.dismiss('cancel');
  };
});

app.controller('ShowPostController', function($scope, $uibModalInstance, postId, PostsService) {
  PostsService.loadPost($scope, 'geometryczna-suknia.html');
  $scope.comments = PostsService.loadComments($scope, 'geometryczna-suknia.html');
  $scope.addComment = function() {
    $scope.comments.push({name:$scope.name,text:$scope.text,email:$scope.email, date: new Date().toLocaleString()});
  }

  $scope.cancel = function() {
     $uibModalInstance.dismiss('cancel');
  };
  $scope.forward = function(state) {
     $uibModalInstance.dismiss({'query': "tags:"+state});
  };
});


