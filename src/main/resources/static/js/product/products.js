import { Pagination } from '/js/common.js'

//페이징 객체 생성
const pagination = new Pagination(10, 10); // 한페이지에 보여줄 레코드건수,한페이지에 보여줄 페이지수

let $productList = '';    // 목록 엘리먼트를 타겟팅
let $loadingImg = '';     // 로딩 이미지

renderHTML();
function renderHTML(){

  const $div = document.createElement('div');
  $div.innerHTML = 
    `
    <div>
      <form id="frm" action="">
          <h3>상품 등록</h3>
          <div>
            <label for="pname">상품명</label>
            <input id="pname" name="pname" type="text">
          </div>
          <div>
            <label for="quantity">수량</label>
            <input id="quantity" name="quantity" type="text">
          </div>
          <div>
            <label for="price">가격</label>
            <input id="price" name="price" type="text">
          </div>
          <div>
            <button id="addBtn" type ="button">등록</button>
          </div>
      </form>
    </div>
    <div id="productList"></div>
    <img id="loading" src='/img/loading.svg'>
    `;
  document.body.appendChild($div);
  const $addBtn = $div.querySelector('#addBtn');
  $addBtn.addEventListener('click', evt => {
    console.log('등록');
    const formData = new FormData($div.querySelector('#frm'));
    const product = {
      pname: formData.get('pname'),
      quantity: formData.get('quantity'),
      price: formData.get('price')
    }
    add(product);
  });

  // 목록
  $productList = $div.querySelector('#productList');
  
  // 로딩 이미지
  $div.style.position = 'relative';
  $loadingImg = $div.querySelector('#loading');
  $loadingImg.style.position = 'absolute';
  $loadingImg.style.top = '50%';
  $loadingImg.style.left = '25%';
  $loadingImg.style.transform = 'translate(-50%, -50%)';
  $loadingImg.style.display = 'none';
  
  list();
}



// 목록 가져오기
async function list() {
  const reqPage = pagination.recordsPerPage;     // 요청 페이지
  const reqCnt = pagination.currentPage;       // 페이지당 레코드 수
  $loadingImg.style.display = 'block';
  const url = `http://localhost:9080/api/products`;
  const option = {
    method: 'GET',
    headers: {
      accept: 'application/json'
    }
  };
  try {
    
    const res = await fetch(url, option);
    if(!res.ok) return new Error('서버 응답 오류');
    const result = await res.json();  //응답메세지 바디를 읽어 json포맷 문자열=>js객체
    if(result.header.rtcd == '00'){
      console.log(result.body);

      const str = result.body.map(item => 
                                      `<div>
                                        <span>${item.productId}</span>
                                        <span>${item.pname}</span>
                                        <span>${item.quantity}</span>
                                        <span>${item.price}</span>
                                      </div>`
                                ).join('');
      $productList.innerHTML = str;

      //총건수는 초기 1회만
      if(!pagination.getTotalRecords()) pagination.setTotalRecords(result.totalCnt);
      pagination.displayPagination(list);

    }else{
      new Error('목록 실패');
    }
    // console.log(result);
  }catch(err){
    console.error(err.message);
  } finally {
    $loadingImg.style.display = 'none';
  }
}
// list();

// 등록
async function add(product) {
  const url = `http://localhost:9080/api/products`;
  const payload = product;
  const option = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'accept': 'application/json'
    },
    body: JSON.stringify(payload)    // js객체=>json포맷 문자열
  };
  try {
    const res = await fetch(url, option);
    if(!res.ok) return new Error('서버 응답 오류');
    const result = await res.json();  //응답메세지 바디
    if(result.header.rtcd == '00'){
      console.log(result.body);
      list();
    }else{
      new Error('등록 실패');
    }
    // console.log(result);
  }catch(err){
    console.error(err.message);
  }
}
// product = {
//   pname: '컴퓨터',
//   quantity: 1,
//   price: 100000 
// };
// add();

// 조회
async function findById(pid) {
  const url = `http://localhost:9080/api/products/${pid}`;
  const option = {
    method: 'GET',
    headers: {
      accept: 'application/json'    // 응답받고자 하는 데이터 포맷타입
    }
  };
  try {
    const res = await fetch(url, option);
    if(!res.ok) return new Error('서버 응답 오류');
    const result = await res.json();  //응답메세지 바디
    // 상품을 찾은 경우
    if(result.header.rtcd == '00'){
      console.log(result.body);
    // 상품을 못 찾은 경우
    }else if(result.header.rtcd == '01'){
      console.log(result.header.rtmsg, result.header.rtdetail);
    }else{
      new Error('조회 실패');
    }
    // console.log(result);
  }catch(err){
    console.error(err.message);
  }
}
// findById(177);

// 수정
async function update(pid, product) {
  const url = `http://localhost:9080/api/products/${pid}`;
  const payload = product;
  const option = {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',   // 요청메세지 바디의 데이터 포맷 타입
      'accept': 'application/json'          // 응답메세지 바디의 데이터 포맷 타입
    },
    body: JSON.stringify(payload)    // js개체 =-> json 문자열
  };
  try {
    const res = await fetch(url, option);
    if(!res.ok) return new Error('서버 응답 오류');
    const result = await res.json();  //응답메세지 바디
    if(result.header.rtcd == '00'){
      console.log(result.body);
    }else{
      new Error('수정 실패');
    }
    // console.log(result);
  }catch(err){
    console.error(err.message);
  }

  // const product = {
  //   pname: '만년필',
  //   quantity: 10,
  //   price: 100000
  // }
}
// update(177, product);

// 삭제
async function deleteById(pid) {
  const url = `http://localhost:9080/api/products${pid}`;
  const option = {
    method: 'DELETE',
    headers: {
      accept: 'application/json'
    }
  };
  try {
    const res = await fetch(url, option);
    if(!res.ok) return new Error('서버 응답 오류');
    const result = await res.json();  //응답메세지 바디
    if(result.header.rtcd == '00'){
      console.log(result.body);
    }else{
      new Error('삭제 실패')
    }
    // console.log(result);
  }catch(err){
    console.error(err.message);
  }
}
// deleteById(177);