console.log('시작');
// 프라미스 객체 : 1. 상태 정보(pending, fullfilled, rejected) 2. 처리결과정보(성공, 실페)
const promise = new Promise((resolvem, reject) => {
  // 비동기로직 위치하는 곳
  // 비동기로지 성공하면
  if(true){
    resolve('성공한 처리결과');
  }else{
    reject('실패 결과');
  }
});

promise.then(res => {console.log(res); return 1;})
       .finally(() => console.log('실패 유무 상관없이 실행1'))
       .then(res => {console.log(res); return new Error('오류1');})
       .finally(() => console.log('실패 유무 상관없이 실행1'))
       .then(res => {console.log(res); return 3;})
       .then(res => console.log(res))
       .catch(err => {console.log(err)});

       console.log('끝');