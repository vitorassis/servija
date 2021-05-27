const call = (url, param={}, callback=(result)=>{}) => {

    param['mode'] = 'cors';
    param['cache'] = 'default';
    param["headers"] = {
        'Content-Type': 'application/json'
    };

    param['body'] = JSON.stringify(param['body']);

    fetch("http://localhost:8080/servija/api"+url, param)

    .then((result)=>{
        return result.json();

    }).then((result)=>{
        callback(result)

    });
}