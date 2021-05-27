$("#loginForm").submit((evt) => {
    evt.preventDefault();

    form = {
        login: evt.target[0].value,
        senha: evt.target[1].value    
    }

    call("/login", {
        body: form,
        method: "post"
    }, (result)=>{
        if(result.success){
            localStorage.setItem("usuario", JSON.stringify(result.obj));
            if(!result.obj.admin)
                window.history.back();
            else
                window.location.href="/admin";
        }else{
            alert(result.message);
        }
    })
})