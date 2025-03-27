	
class Solution {
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {

    List<String> result = new ArrayList<>();
    // recipes + ingredients => Map<recipes, Set<ingredients>>
    Map<String, Set<String>> map = new HashMap<>();
    for(int i=0; i<recipes.length; i++){
        Set<String> ingredientsSet = new HashSet<>(ingredients.get(i));
        map.put(recipes[i], ingredientsSet);
    }
    // supplies => suppliesSet
    Set<String> suppliesSet = new HashSet<>(Arrays.asList(supplies));

    boolean toggle = true;
    // toggle이 켜져있는 동안 반복
        // toggle을 끔.
        // 맵 순회하면서 
            // ingredients 를 순회. 
                // ingredient가 suppliesSet에 있으면 해당 ingredient 삭제.
                // iterator로 안전하게 삭제
            // 순회하고 ingredients 사이즈가 0이 되면 
                // key값인 recipe를 suppliesSet에 추가
                // result에 recipe를 추가
                // suppliesSet이 추가됐다는 toggle을 킴
                // 해당 Entry 안전하게 삭제

    while(toggle){
        toggle = false;
        Iterator<Map.Entry<String, Set<String>>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Set<String>> entry = iterator.next();
            Set<String> ingredientsSet = entry.getValue();
            ingredientsSet.removeAll(suppliesSet);
            // Iterator<String> setIterator = ingredientsSet.iterator();
            // while(setIterator.hasNext()){
            //     String ingredient = setIterator.next();
            //     if(suppliesSet.contains(ingredient)){
            //         setIterator.remove();
            //     }
            // }
            if(ingredientsSet.isEmpty()){
                String entRecipe = entry.getKey();
                result.add(entRecipe);
                suppliesSet.add(entRecipe);
                toggle = true;
                iterator.remove();
            }
        }
    }
    return result;
}
}
