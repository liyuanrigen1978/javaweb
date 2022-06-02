//删除处理
function delUser(id) {
    if(confirm('是否删除')){
        window.location.href='user.do?id='+id+'&operate=del';
    }
}

//分页处理
function  page(pageNo) {
    window.location.href="user.do?pageNo="+pageNo;
}