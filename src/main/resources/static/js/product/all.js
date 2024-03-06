// type이 module인 스크립트에서만 import 구문 가능
import {Pagination} from '/js/common.js'

//페이징 객체 생성
const pagination = new Pagination(10, 10); // 한페이지에 보여줄 레코드건수,한페이지에 보여줄 페이지수
// 총 레코드 건수
const totalCnt = window.totalCnt;
const cpgs = window.cpgs;
const cp = window.cp;

pagination.setCurrentPageGroupStart(cpgs); //페이지 그룹 시작번호
pagination.setCurrentPage(cp); //현재 페이지
pagination.setTotalRecords(totalCnt); //총레코드 건수
pagination.displayPagination(nextPage);

function nextPage() {
  const reqPage = pagination.currentPage;   // 요청 페이지
  const reqCnt = pagination.recordsPerPage; // 페이지당 레코드 수

  const cpgs = pagination.currentPageGroupStart;    // 페이지 그룹 시작 번호
  const cp = pagination.currentPage;                // 현재 페이지

  location.href = `/products?reqPage=${reqPage}&reqCnt=${reqCnt}&cpgs=${cpgs}&cp=${cp}`;
}

const $addBtn = document.getElementById('addBtn');
$addBtn.addEventListener('click', e => {
  location.href = '/products/add';                // get http://loclahost:9080/products/add
});

const $rows = document.getElementById('rows');
$rows.addEventListener('click', evt => {
  // input 요소이면서 checkbox는 제외
  if(evt.target.tagName === 'INPUT' && evt.target.getAttribute('type') == 'checkbox') {
    return;
  }
  // 2) input 요소 중에 checkbox 있는 td는 제외
  if(evt.target.tagName == 'TD' && evt.target.classList.contains('chk')){
    return;
  }
  const $row = evt.target.closest('.row');  // closest() : 이벤트가 발생한 엘리먼트로부터 가장 가까운 상위 엘리먼트를 찾기
  const productId = $row.dataset.productId;
  location.href = `/products/${productId}/detail`;    // GET http://localhost:9080/상품번호/detail
});

// 삭제
const frm = document.getElementById('frm');
frm.addEventListener('submit', evt => {
  evt.preventDefault();    // 기본 이벤트(submit) 중지
  if(!window.confirm('삭제하시겠습니까?')) return;
  evt.target.submit();
});

// 전체 선택
// 일부 체크박스가 체크되어 있으면 전체 언체크, 아니라면 모든 체크박스를 체크
const $selectAll = document.getElementById('selectAll');
$selectAll.addEventListener('click', evt => {
  // Array.from(iteral) : iteral 객체를 배열로 변환
  const $inputs = Array.from(document.querySelectorAll('#rows input[type=checkbox]'));  // nodelist를 배열로 변경
  const isSomeChecked = $inputs.some(inputEle => inputEle.checked == true)
  if(isSomeChecked){
    $inputs.forEach(input => input.checked = false);   // 일부 체크박스가 uncheck면 모든 체크박스를 unchecked로 변경
  }else {
    $inputs.forEach(input => input.checked = true);   // 모든 체크박스를 checked로 변경
  }
});