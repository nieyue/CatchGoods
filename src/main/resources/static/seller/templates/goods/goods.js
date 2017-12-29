/**
 * 物品JS
 */
//初始化物品籍频道
mainApp.value('goodsRecommend',[
	{id:0,value:'不推荐'},
	{id:1,value:'封推'},
	{id:2,value:'精推'},
	{id:3,value:'优推'}
	]);
mainApp.config(function ($stateProvider, $urlRouterProvider) {
	var requestDomainUrl="http://"+domainManager.CatchGoods;//请求数据url
	//var requestDomainUrl="http://"+location.hostname+":8082";//请求数据url
	//var requestDomainUrl="http://goods.newzhuan.cn";//请求数据url
	var imgUploadDomainUrl="http://"+domainManager.MyWangEditor;//请求图片上传url
     	$stateProvider
     	.state("main.goodsCateList", {//物品类型列表
            url:"/goodsCateList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/goods/goods_cate_list.html",
                    controller:function($rootScope,$scope,$state){
            /*
            *列表
            */
            $scope.goodsCateList=[]; 		
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
			  $scope.goodsCateListInit();
			  }
			};
			$scope.goodsCateListInit=function(){
			  $.get(requestDomainUrl+"/goodsCate/count",function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/goodsCate/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.goodsCateList=pld;
			 console.log($scope.goodsCateList)
			$scope.$apply();
               });
       });
			};
			$scope.goodsCateListInit();
			/*
            *列表end
            */
			/*
            *修改
            */
            $scope.updateGoodsCate=function(goodsCate){
            $state.go("main.goodsCateUpdate",{goodsCateId:goodsCate.goodsCateId});
            };
			/*
            *修改end
            */
			/*
            *删除
            */
            $scope.delGoodsCate=function(goodsCate){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/goodsCate/delete?goodsCateId="+goodsCate.goodsCateId,function(data){
       					console.log($scope.goodsCateList)
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("删除成功", function(){
       	   				location.reload();
       	   				}); 
       	   				
       	   				}else{
       	   					myUtils.myLoadingToast("删除失败");   	   				
       	   				}
       	   			});
       			});
            };
			/*
            *删除end
            */
                    }
                } 
            }
        })
     	.state("main.goodsCateAdd", {//物品类型增加
            url:"/goodsCateAdd",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/goods/goods_cate_add.html",
                    controller:function($rootScope,$scope,$state){
				    $scope.goodsCate={
				    name:''
				    };
                     
                     //表单提交
                    $scope.addGoodsCateForm=function(){
                    	$.ajax({
						  url: requestDomainUrl+"/goodsCate/add",
						  type: 'POST',
						  data: $scope.goodsCate,
						  success: function(data){
						  if(data.code==200){
							  $scope.goodsCate={
									    name:''
									    };
       	   				$scope.myAddGoodsCateForm.$setPristine();
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
        .state("main.goodsCateUpdate", {//更新
            url:"/goodsCateUpdate/:goodsCateId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/goods/goods_cate_update.html",
                    controller:function($rootScope,$scope,$state){
                  	/*
            		*获取参数goodsCateId
            		*/
                    $scope.updateGoodsCateId=$state.params.goodsCateId;
                    console.log($scope.updateGoodsCateId)
                     if(!$scope.updateGoodsCateId||!myUtils.userVerification.catNum.test($scope.updateGoodsCateId)){
                    $state.go("main.goodsCateList");
                    return;
                    } 
                    /*
            		*获取参数goodsCateId end
            		*/
            		/*
            		*初始化goodsCate
            		*/
                    $scope.updateInit=function(goodsCateId){
                    $.get(requestDomainUrl+"/goodsCate/"+goodsCateId,function(data){
       	   				if(data.code==200){
       	   				$scope.goodsCate=data.list[0];
       	   				$scope.$apply();
       	   				myUtils.myLoadingToast("加载成功" ); 
       	   				}else{
       	   					myUtils.myLoadingToast("加载失败");   	   				
       	   				}
       	   			});
                    };
                    $scope.updateInit($scope.updateGoodsCateId);
                    /*
            		*初始化goodsCate end
            		*/
            		/*
            		*修改goodsCate提交
            		*/
            		$scope.updateGoodsCateForm=function(){
            		$.post(requestDomainUrl+"/goodsCate/update",
            				JSON.parse(angular.toJson($scope.goodsCate)),
            				function(data){
            			if(data.code==200){
            				myUtils.myLoadingToast("修改成功"); 
            			}else{
            				myUtils.myLoadingToast("修改失败");   	   				
            			}
            		});
           		};
            		/*
            		*修改goodsCate提交 end
            		*/
                    }
                } 
            }
        })
        .state("main.goodsList", {//列表
            url:"/goodsList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/goods/goods_list.html",
                    controller:function($rootScope,$scope,$state,goodsRecommend){
                    	$scope.goodsRecommend=goodsRecommend;//注入
            //列表
            
            $scope.goodsList=[]; 		
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
			  //$scope.showgoodsCateListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.goodsListInit();
			  }
			};
			$scope.goodsListInit=function(){
			  $.get(requestDomainUrl+"/goods/count",function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/goods/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.goodsList=pld;
			 console.log($scope.goodsList)
			$scope.$apply();
               });
       });
			};
			$scope.goodsListInit();
			
            //列表end
            
			
            //修改
            
            $scope.updateGoods=function(goods){
            $state.go("main.goodsUpdate",{goodsId:goods.goodsId});
            };
			
            //修改end
            
			
            //删除
            
            $scope.delGoods=function(goods){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/goods/delete?goodsId="+goods.goodsId,function(data){
       					console.log($scope.goodsList)
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("删除成功", function(){
       	   				location.reload();
       	   				}); 
       	   				
       	   				}else{
       	   					myUtils.myLoadingToast("删除失败");   	   				
       	   				}
       	   			});
       			});
            };
			
            //删除end
            
                    }
                } 
            }
        })
        .state("main.goodsUpdate", {//更新
            url:"/goodsUpdate/:goodsId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/goods/goods_update.html",
                    controller:function($rootScope,$scope,$state,goodsRecommend){
                    	$scope.goodsRecommend=goodsRecommend;//注入
            		//获取参数goodsId
            		
                    $scope.updateGoodsId=$state.params.goodsId;
                   // console.log($scope.updateGoodsId)
                     if(!$scope.updateGoodsId||!myUtils.userVerification.catNum.test($scope.updateGoodsId)){
                    $state.go("main.goodsList");
                    return;
                    } 
                    
            		//获取参数goodsId end
            		
                    
                     //获取参数goodsCate 
                    $scope.goodsCateListInit=function(){
                        $.get(requestDomainUrl+"/goodsCate/list?orderWay=asc&pageSize=100000",function(data){
           	   				if(data.code==200){
           	   				 $scope.goodsCateList=data.list;
           	   				$scope.$apply();
           	   				myUtils.myLoadingToast("加载成功" ); 
           	   				}else{
           	   					myUtils.myLoadingToast("加载失败");   	   				
           	   				}
           	   			});
                        };
                        $scope.goodsCateListInit();
                    
                     //获取参数goodsCate end
                     
            		
            		//初始化goods
            		
                    $scope.updateInit=function(goodsId){
                    $.get(requestDomainUrl+"/goods/"+goodsId,function(data){
                    	if(data.code==200){
                    		$scope.goods=data.list[0];
                    		$scope.$apply();
                    		myUtils.myLoadingToast("加载成功" ); 
                    	}else{
                    		myUtils.myLoadingToast("加载失败");   	   				
                    	}
                    });
                    };
                    $scope.updateInit($scope.updateGoodsId);
                    
            		//初始化goodsCate end
            		
            		
            		//修改goods提交
            		
            		$scope.updateGoodsForm=function(){
            			console.log($scope.goods)
            		$.ajax({
            			url:requestDomainUrl+"/goods/update",
            			type:'POST',
            			contentType:'application/json',
                    	data:angular.toJson($scope.goods),
                    	//data:JSON.parse(angular.toJson($scope.goods)),
                    	success:function(data){
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("修改成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("修改失败");   	   				
       	   				}
       	   				}});
            		};
            		//修改goods提交 end
            		 
            		//上传物品图片
            		//上传文章封面图片
             		$("#goodsImgFileUpload").change(function(){
             			if(($scope.imgConfigWidth && $scope.imgConfigHeight)
             					&&($scope.imgConfigWidth>1200
             					||$scope.imgConfigWidth<=0
             					||$scope.imgConfigHeight<=0
             					||$scope.imgConfigHeight>1200)
             					){
             			myUtils.myLoadingToast("图片尺寸不符合");
            			 return;
            			 }
             			myUtils.fileUpload(
             				    {inputfile:$("#goodsImgFileUpload"),
             				    ajaxObj:{
             				        formData:[
             				            {key:"editorUpload",value:$("#goodsImgFileUpload").get(0).files[0]}
             				            ],
             				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidth+"&height="+$scope.imgConfigHeight,
             				        success:function(data){
             				            if(data){
             				            myUtils.myPrevToast("上传成功",null,"remove");
             				           $scope.goods.imgAddress=data;
             				          $rootScope.formDisabled=false;
             				            $scope.$apply();
             				            }
             				        }
             				    }
             				}
             				);
             		});
            		
                    }
                } 
            }
        })
        .state("main.goodsAdd", {//物品增加
            url:"/goodsAdd",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/goods/goods_add.html",
                    controller:function($rootScope,$scope,$state,goodsRecommend){
                    $scope.goodsRecommend=goodsRecommend;//注入
				    $scope.goods={
				    goodsName:'',
				    goodsCateId:'',
				    imgAddress:'',
				    cameraNumber:1,
				    singleCharge:2,
				    recommend:0,
				    status:1
				    };
                     //获取参数goodsCate 
                    $scope.goodsCateListInit=function(){
                        $.get(requestDomainUrl+"/goodsCate/list?orderWay=asc&pageSize=100000",function(data){
           	   				if(data.code==200){
           	   				 $scope.goodsCateList=data.list;
           	   			$scope.goods.goodsCateId=$scope.goodsCateList[0].goodsCateId;
           	   				$scope.$apply();
           	   				myUtils.myLoadingToast("加载成功" ); 
           	   				}else{
           	   					myUtils.myLoadingToast("加载失败");   	   				
           	   				}
           	   			});
                        };
                        $scope.goodsCateListInit();
                     //获取参数goodsCate end
                     
                		//上传文章封面图片
                 		$("#goodsImgFileUpload").change(function(){
                 			if(($scope.imgConfigWidth && $scope.imgConfigHeight)
                 					&&($scope.imgConfigWidth>1200
                 					||$scope.imgConfigWidth<=0
                 					||$scope.imgConfigHeight<=0
                 					||$scope.imgConfigHeight>1200)
                 					){
                 			myUtils.myLoadingToast("图片尺寸不符合");
                			 return;
                			 }
                 			myUtils.fileUpload(
                 				    {inputfile:$("#goodsImgFileUpload"),
                 				    ajaxObj:{
                 				        formData:[
                 				            {key:"editorUpload",value:$("#goodsImgFileUpload").get(0).files[0]}
                 				            ],
                 				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidth+"&height="+$scope.imgConfigHeight,
                 				        success:function(data){
                 				            if(data){
                 				            myUtils.myPrevToast("上传成功",null,"remove");
                 				           $scope.goods.imgAddress=data;
                 				          $rootScope.formDisabled=false;
                 				            $scope.$apply();
                 				            }
                 				        }
                 				    }
                 				}
                 				);
                 		});
                        
                     //表单提交
                    $scope.addGoodsForm=function(){
                    	$.ajax({
						  url: requestDomainUrl+"/goods/add",
						  type: 'POST',
						  contentType:'application/json',
						  data: angular.toJson($scope.goods),
						  success: function(data){
						  if(data.code==200){
							  $scope.goods={
									    goodsName:'',
									    goodsCateId:$scope.goodsCateList[0].goodsCateId,
									    imgAddress:'',
									    cameraNumber:1,
									    singleCharge:2,
									    recommend:0,
									    status:1
									    };
       	   				$scope.myAddGoodsForm.$setPristine();
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
        .state("main.goodsOrderList", {//列表
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
			  //$scope.showgoodsCateListIcon=true;
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
        })
        .state("main.goodsOrderAcount", {//物品订单人
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
        });
     	});	