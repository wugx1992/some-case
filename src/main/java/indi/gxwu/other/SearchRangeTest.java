package indi.gxwu.other;

import java.util.UUID;

/**
 * @Author: gx.wu
 * @Date: 2019/12/24 17:29
 * @Description: 区间搜索
 */
public class SearchRangeTest {

    public static void main(String[] args) {
        SearchRangeTest t = new SearchRangeTest();
        int[] A = new int[]{};
        int[] result = t.searchRange(A, 9);
//        int[] result = t.searchRange2(A, 9);
        System.out.println("["+result[0]+","+result[1]+"]");

        A = new int[]{5, 7, 7, 8, 8, 10};
        result = t.searchRange(A, 8);
//        result = t.searchRange2(A, 8);
        System.out.println("["+result[0]+","+result[1]+"]");

    }

    public int[] searchRange(int[] A, int target){
        int[] result = new int[]{-1,-1};
        if(A.length==0){
            return result;
        }

        int left = 0, right = A.length-1;
        while (left<=right){
            int mid = left+(right-left)/2;
            if(mid<0){
                break;
            }
            if(A[mid] == target){
                result[0] = mid;
                result[1] = mid;
                int min = searchMin(A, left, mid-1, target);
                int max = searchMax(A, mid+1, right, target);
                if(min!=-1){
                    result[0] = min;
                }
                if(max!=-1){
                    result[1] = max;
                }break;

            }else if(A[mid]>target){
                right = mid-1;
            }else{
                left = mid+1;
            }
        }
        return result;
    }

    public int searchMin(int[] A, int startIndex, int endIndex, int target){
        int left = startIndex, right = endIndex;
        while (left<=right){
            int mid = left+(right-left)/2;
            if(mid<0){
                break;
            }
            if(A[mid]==target){
                int tempMin = searchMin(A, startIndex, mid-1, target);
                if(tempMin==-1){
                    return mid;
                }else{
                   return tempMin;
                }
            }else if(A[mid]>target){
                right = mid-1;
            }else{
                left = mid+1;
            }
        }
        return -1;
    }

    public int searchMax(int[] A, int startIndex, int endIndex, int target){
        int left = startIndex, right = endIndex;
        while (left<=right){
            int mid = left+(right-left)/2;
            if(mid<0){
                break;
            }
            if(A[mid]==target){
                int tempMax = searchMax(A, mid+1, endIndex, target);
                if(tempMax==-1){
                    return mid;
                }else{
                   return tempMax;
                }
            }else if(A[mid]>target){
                right = mid-1;
            }else{
                left = mid+1;
            }
        }
        return -1;
    }

    public int[] searchRange2(int[] A, int target){
        int[] result = new int[] {-1,-1};
        for(int i=0;i<A.length;i++){
            if(A[i]==target){
                if(result[0]==-1){
                    result[0] = i;
                    result[1] = i;
                }else{
                    result[1] = i;
                }
            }
        }
        return result;
    }
}
