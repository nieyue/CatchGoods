/**
 * 充值JS
 */
//初始化充值籍频道1支付宝支付，2微信支付，3银联支付
mainApp.value('rechargeTypes',
   [{id:1,value:'支付宝支付'},
	{id:2,value:'微信支付'},
	{id:3,value:'银联支付'}
	]);
mainApp.config(function ($stateProvider, $urlRouterProvider) {
	var requestDomainUrl="http://"+domainManager.CatchGoods;//请求数据url
	//var requestDomainUrl="http://"+location.hostname+":8082";//请求数据url
     	$stateProvider
        .state("main.rechargeTermList", {//列表
            url:"/rechargeTermList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/recharge/recharge_term_list.html",
                    controller:function($rootScope,$scope,$state,rechargeTypes){
                    	$scope.rechargeTypes=rechargeTypes;//注入
            //列表
            $scope.rechargeTermList=[]; 		
    		$scope.noMore=false;//false还有，true没有更多
    		$scope.totalNumber=0;//总管理员数目
 			$scope.showPageNumberArray=[];//显示页面循环的数组 类似 1,2,3,4,5
			$scope.totalPage=0;//总页数
				
			$scope.currentPage=1;//当前页
			$scope.pageNumber=10;//每页显示数目
			$scope.mostShowPageNumber=5;//设定最多显示页码数目	
			$scope.pagination=myUtils.myPaginationHandler();
			
			//点击哪页显示哪页
			$scope.toPage=function(currentPageNumber){
			  if($scope.pagination.toPage(currentPageNumber,$scope.currentPage,$scope.totalPage)){
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.rechargeTermListInit();
			  }
			};
		$scope.rechargeTermListInit=function(){
		  $.get(requestDomainUrl+"/rechargeTerm/count",function(cd){
       		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
          //初始化
         $.get(requestDomainUrl+"/rechargeTerm/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.rechargeTermList=pld;
		  //console.log($scope.rechargeTermList)
			$scope.$apply();
               });
       });
			};
			$scope.rechargeTermListInit();
            //列表end
			
			/*
	            *修改
	            */
	            $scope.updateRechargeTerm=function(rechargeTerm){
	            $state.go("main.rechargeTermUpdate",{rechargeTermId:rechargeTerm.rechargeTermId});
	            };
				/*
	            *修改end
	            */
                    }
                } 
            }
        })
        	.state("main.rechargeTermAdd", {//物品类型增加
            url:"/rechargeTermAdd",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/recharge/recharge_term_add.html",
                    controller:function($rootScope,$scope,$state){
				    $scope.rechargeTerm={
				    title:'',
				    rechargeMoney:10,
				    giveMoney:150,
				    status:1
				    };
                     
                     //表单提交
                    $scope.addRechargeTermForm=function(){
                    	$.ajax({
						  url: requestDomainUrl+"/rechargeTerm/add",
						  type: 'POST',
						  data: $scope.rechargeTerm,
						  success: function(data){
						  if(data.code==200){
							  $scope.rechargeTerm={
									    title:'',
									    rechargeMoney:10,
									    giveMoney:150,
									    status:1
									    };
       	   				$scope.myAddRechargeTermForm.$setPristine();
       	   				$scope.$apply();
       	   				myUtils.myLoadingToast("添加成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("添加失败");   	   				
       	   				}
						  },
						  error: function(){
						  myUtils.myLoadingToast("添加失败"); 
						  }
						});
                    };
                    }
                } 
            }
        })
         .state("main.rechargeTermUpdate", {//更新
            url:"/rechargeTermUpdate/:rechargeTermId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/recharge/recharge_term_update.html",
                    controller:function($rootScope,$scope,$state){
                  	/*
            		*获取参数rechargeTermId
            		*/
                    $scope.updateRechargeTermId=$state.params.rechargeTermId;
                    console.log($scope.updateRechargeTermId)
                     if(!$scope.updateRechargeTermId||!myUtils.userVerification.catNum.test($scope.updateRechargeTermId)){
                    $state.go("main.rechargeTermList");
                    return;
                    } 
                    /*
            		*获取参数rechargeTermId end
            		*/
            		/*
            		*初始化rechargeTerm
            		*/
                    $scope.updateInit=function(rechargeTermId){
                    $.get(requestDomainUrl+"/rechargeTerm/"+rechargeTermId,function(data){
       	   				if(data.code==200){
       	   				$scope.rechargeTerm=data.list[0];
       	   				$scope.$apply();
       	   				myUtils.myLoadingToast("加载成功" ); 
       	   				}else{
       	   					myUtils.myLoadingToast("加载失败");   	   				
       	   				}
       	   			});
                    };
                    $scope.updateInit($scope.updateRechargeTermId);
                    /*
            		*初始化rechargeTerm end
            		*/
            		/*
            		*修改rechargeTerm提交
            		*/
            		$scope.updateRechargeTermForm=function(){
            		$.post(requestDomainUrl+"/rechargeTerm/update",
            				JSON.parse(angular.toJson($scope.rechargeTerm)),
            				function(data){
            			if(data.code==200){
            				myUtils.myLoadingToast("修改成功"); 
            			}else{
            				myUtils.myLoadingToast("修改失败");   	   				
            			}
            		});
           		};
            		/*
            		*修改rechargeTerm提交 end
            		*/
                    }
                } 
            }
        })
        .state("main.rechargeRecordList", {//列表
            url:"/rechargeRecordList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/recharge/recharge_record_list.html",
                    controller:function($rootScope,$scope,$state,rechargeTypes){
                    	$scope.rechargeTypes=rechargeTypes;//注入
            //列表
            $scope.rechargeRecordList=[]; 		
    		$scope.noMore=false;//false还有，true没有更多
    		$scope.totalNumber=0;//总管理员数目
 			$scope.showPageNumberArray=[];//显示页面循环的数组 类似 1,2,3,4,5
			$scope.totalPage=0;//总页数
				
			$scope.currentPage=1;//当前页
			$scope.pageNumber=10;//每页显示数目
			$scope.mostShowPageNumber=5;//设定最多显示页码数目	
			$scope.pagination=myUtils.myPaginationHandler();
			
			//点击哪页显示哪页
			$scope.toPage=function(currentPageNumber){
			  if($scope.pagination.toPage(currentPageNumber,$scope.currentPage,$scope.totalPage)){
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.rechargeRecordListInit();
			  }
			};
		$scope.rechargeRecordListInit=function(){
		  $.get(requestDomainUrl+"/rechargeRecord/count",function(cd){
       		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
          //初始化
         $.get(requestDomainUrl+"/rechargeRecord/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.rechargeRecordList=pld;
		  //console.log($scope.rechargeRecordList)
			$scope.$apply();
               });
       });
			};
			$scope.rechargeRecordListInit();
            //列表end
                    }
                } 
            }
        })
  /*      .state("main.goodsOrderList", {//列表
            url:"/goodsOrderList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/goods/goods_order_list.html",
                    controller:function($rootScope,$scope,$state){
            //列表
            $scope.goodsOrderList=[]; 		
    		$scope.noMore=false;//false还有，true没有更多
    		$scope.totalNumber=0;//总管理员数目
 			$scope.showPageNumberArray=[];//显示页面循环的数组 类似 1,2,3,4,5
			$scope.totalPage=0;//总页数
				
			$scope.currentPage=1;//当前页
			$scope.pageNumber=10;//每页显示数目
			$scope.mostShowPageNumber=5;//设定最多显示页码数目	
			$scope.pagination=myUtils.myPaginationHandler();
			
			//点击哪页显示哪页
			$scope.toPage=function(currentPageNumber){
			  if($scope.pagination.toPage(currentPageNumber,$scope.currentPage,$scope.totalPage)){
			  //$scope.showrechargeTermListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.goodsOrderListInit();
			  }
			};
			$scope.goodsOrderListInit=function(){
			  $.get(requestDomainUrl+"/goodsOrder/count",function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/goodsOrder/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.goodsOrderList=pld;
			 console.log($scope.goodsOrderList)
			$scope.$apply();
               });
       });
			};
			$scope.goodsOrderListInit();
			
            //列表end
            
			
            //修改订单状态 状态，状态，0已下单，1已支付，2申请退款，3已退款，4拒绝退款,5已完成
            
			//$scope.showStatus=0;//默认0显示结果，1，显示修改，
			$scope.changeStatus=function(goodsOrderDetail){
				goodsOrderDetail.$$status=1;//默认0显示结果，1，显示修改，
				//$scope.showStatus=1;
			};
			$scope.goodsOrderDetailStatus=[
				{id:0,value:'已下单'},
				{id:1,value:'已发货'}
				];
            $scope.updateGoodsOrderDetailStatus=function(goodsOrderDetail){
            	$.get(requestDomainUrl+"/goodsOrderDetail/update",
            			{
            				goodsOrderDetailId:goodsOrderDetail.goodsOrderDetailId,
            				status:goodsOrderDetail.status
            			}
            			,function(data){
                	if(data.code==200){
                		location.reload();
                		myUtils.myLoadingToast("加载成功" ); 
                	}else{
                		myUtils.myLoadingToast("加载失败");   	   				
                	}
                });
            };
			
            //修改end
            
                    }
                } 
            }
        })*/
      /*  .state("main.goodsOrderAcount", {//充值订单人
            url:"/goodsOrderAcount/:acountId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/goods/goods_order_acount.html",
                    controller:function($rootScope,$scope,$state){
                  	
            		//获取参数acountId
                    $scope.acountId=$state.params.acountId;
                     if(!$scope.acountId||!myUtils.userVerification.catNum.test($scope.acountId)){
                    $state.go("main.goodsOrderList");
                    return;
                    } 
            		//获取参数acountId end
            		
            		
            		//初始化acount
                    $scope.acountInit=function(acountId){
                    $.get("/acount/"+acountId,function(data){
       	   				if(data.code==200){
       	   				$scope.acount=data.list[0];
       	   				$scope.$apply();
       	   				console.log($scope.acount)
       	   				myUtils.myLoadingToast("加载成功" ); 
       	   				}else{
       	   					myUtils.myLoadingToast("加载失败");   	   				
       	   				}
       	   			});
                    };
                    $scope.acountInit($scope.acountId);
            		//初始化acount end
            		
                    }
                } 
            }
        })*/;
     	});	