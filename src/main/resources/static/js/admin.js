function onClickModifyRoleEventHandler(id) {
   document.getElementById(id).setAttribute("action", "/admin/doModifyUserRole");
   document.getElementById(id).submit();
}

function onClickUrlEventHandler(id) {
   document.getElementById(id).setAttribute("action", "/admin/doDeleteUser");
   document.getElementById(id).submit();
}

function onClickCheckBoxEvnetHandler(id){
   const checkboxList = document.getElementsByClassName(id);
   let isUnauth = false;
   let isChecked = true;
   
   
   
   for(let index = 0; index < checkboxList.length; index++){
      const checkboxObj = checkboxList[index];
      if(checkboxObj.value.trim() == "ROLE_UNAUTH" && checkboxObj.checked == true){
         isUnauth = true;
      }
      
      if(checkboxObj.checked == true){
         isChecked = false;
      }
      
   }
   
   if(isChecked){
      alert("최소 하나의 권한 설정 필요")
      for(let index = 0; index < checkboxList.length; index++){
         const checkboxObj = checkboxList[index];
         checkboxObj.checked = false;
         checkboxObj.disabled = false;
         if(checkboxObj.value.trim() == "ROLE_STUDENT"){
            checkboxObj.checked = true;
         }
      }   
   }
   
   if(isUnauth){
      for(let index = 0; index < checkboxList.length; index++){
         const checkboxObj = checkboxList[index];
         if(checkboxObj.value.trim() != "ROLE_UNAUTH"){
            checkboxObj.checked = false;
            checkboxObj.setAttribute("disabled", true);
         }
      }   
   } else {
      for(let index = 0; index < checkboxList.length; index++){
         const checkboxObj = checkboxList[index];
         if(checkboxObj.disabled){
            checkboxObj.disabled = false;
            if(checkboxObj.value.trim() == "ROLE_STUDENT"){
               checkboxObj.checked = true;
            }
         }
      }
   }
}
