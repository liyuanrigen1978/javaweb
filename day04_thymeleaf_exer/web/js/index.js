function delUser(id) {
    if(confirm('是否删除')){
        window.location.href='del.do?id='+id;
    }
}