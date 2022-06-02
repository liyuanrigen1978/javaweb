//删除处理
function delUser(id) {
    if(confirm('是否删除')){
        window.location.href='del.do?id='+id;
    }
}

//分页处理
function  page(pageNo) {
    window.location.href='index?pageNo='+pageNo;
}