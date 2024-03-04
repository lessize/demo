{
  console.log('start');
  console.log('loading');
  let sum = 0;
  for(let i=0; i<990000000; i++){
    sum += i;
  };
  console.log(`sum = ${sum}`);
  console.log('end');
}
console.log('---------------------------');
// { // 비동기1
//   console.log('start');
//   console.log('loading');
//   async function method1() {
//     const result = await new Promise((resolve, reject) => {
//       // 비동기 처리 로직, 보통 시간이 소요되는 작업 ex) 서버와의 통신
//       let sum = 0;
//       for(let i=0; i<990000000; i++){
//         sum += i;
//       };
//       resolve(sum);
//     });
//     console.log(result);
//   }
//   method1();
//   console.log('end');
// }
console.log('---------------------------');
{ // 비동기2
  console.log('start');
  console.log('loading');
  async function method1() {
    try{
      const result = await new Promise((resolve, reject) => {
        // 비동기 처리 로직, 보통 시간이 소요되는 작업 ex) 서버와의 통신
        let sum = 0;
        for(let i=0; i<990000000; i++){
          sum += i;
        };
        if(false){
          resolve(sum);
        }else{
          reject(new Error('오류발생'));
        }
      });
      console.log(result);
    }catch(err){
      console.log(err);
    }finally{
      console.log('성공유무상관없이 수행됨');
    }
  }
  method1();
  console.log('end');
}