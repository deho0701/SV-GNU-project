function submit(){
    var loginForm = document.loginForm;
            var ID = loginForm.ID.value;    
            var PW = loginForm.PW.value;
            
            if(!userId || !password){
                alert("아이디와 비밀번호를 모두 입력해주세요.")
            }else{
                loginForm.submit();
            }        
}
